package com.example.peeringbyakugan.home

import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.AnimeRepository
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.database.DatabaseAnime
import com.example.peeringbyakugan.network.*
import com.example.peeringbyakugan.network.scheduleDataModels.DayItem
import com.example.peeringbyakugan.network.scheduleDataModels.ScheduleResponse
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResponse
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
import kotlinx.coroutines.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST", "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomeViewModel(appComponent: AppComponent) : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _currentAnimeList = MutableLiveData<List<SearchOnlyResultsItem>>()
    val currentAnimeList: MutableLiveData<List<SearchOnlyResultsItem>> get() = _currentAnimeList

    private val _animeRetrievalAttemptCompleted = MutableLiveData<Boolean>()
    val animeRetrievalAttemptCompleted: LiveData<Boolean> get() = _animeRetrievalAttemptCompleted

    private val _animeRetrievalSuccessful = MutableLiveData<Boolean>()
    val animeRetrievalSuccessful: LiveData<Boolean> get() = _animeRetrievalSuccessful

    private val _seekBarValue = MutableLiveData<Float>()
    val seekBarValue: LiveData<Float> get() = _seekBarValue


    @Inject
    lateinit var jikanIO: Jikan

    @Inject
    lateinit var cm: ConnectivityManager

    @Inject
    lateinit var animeRepo: AnimeRepository


    init {
        appComponent.inject(this)
        _animeRetrievalAttemptCompleted.value = true
        _seekBarValue.value = 0.0f

    }


    fun queryJikanSearchAndFilter(query: String, genreList: String, score: String, orderBy: String) {
        scope.launch {
            animeRepo.saveBookmarkedAnime(
                DatabaseAnime(
                    1,
                    "My Anime",
                    true,
                    "Friday the 13th",
                    "https://noneyabusiness",
                    System.currentTimeMillis()
                )
            )
            try {
                val response: SearchOnlyResponse = jikanIO.getAnimeListAsync(query, genreList, score, orderBy).await()
                val list: List<SearchOnlyResultsItem?>? = response.results
                _currentAnimeList.value = list as List<SearchOnlyResultsItem>?

//                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.title}")
//                Log.d("HomeViewModel", "And it's synopsis is: ${list?.get(0)?.synopsis}")

            } catch (t: Throwable) {
                Log.d("HomeViewModel", "${t.message}")
                progressBarInvisible()
                _animeRetrievalSuccessful.value = false
            }

        }
    }

    fun queryJikanSchedule(day: String) {
        scope.launch {
            try {
                val response: ScheduleResponse = jikanIO.getScheduleAsync(day).await()
                var list: List<DayItem?>? = null
                when (day) {
                    "Monday" -> list = response.monday
                    "Tuesday" -> list = response.tuesday
                    "Wednesday" -> list = response.wednesday
                    "Thursday" -> list = response.thursday
                    "Friday" -> list = response.friday
                    "Saturday" -> list = response.saturday
                    "Sunday" -> list = response.sunday
                }

                _currentAnimeList.value = list.toSearchItem() as List<SearchOnlyResultsItem>?
                _animeRetrievalSuccessful.value = true


            } catch (t: Throwable) {
                Log.d("HomeViewModel", "${t.message}")
                progressBarInvisible()
                _animeRetrievalSuccessful.value = false
            }

        }
    }

    fun progressBarVisible() {
        _animeRetrievalAttemptCompleted.value = false
    }


    fun progressBarInvisible() {
        _animeRetrievalAttemptCompleted.value = true
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun setSeekBarValue(value: Float) {
        _seekBarValue.value = value
    }


    companion object {

        val daysList = listOf("None", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
        val orderList = listOf("Title", "Start Date", "Rating", "No of Episodes")
        val orderListBundle = Bundle().apply {
            putString(orderList[0], "title")
            putString(orderList[1], "start_date")
            putString(orderList[2], "score")
            putString(orderList[3], "episodes")
        }

    }

    private fun List<DayItem?>?.toSearchItem(): List<SearchOnlyResultsItem?>? {
        return this?.map {
            SearchOnlyResultsItem(
                null, it?.imageUrl, it?.malId, null, it?.title, null,
                null, null, null, null, true, null, it?.airingStart
            )

        }
    }


    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }


}