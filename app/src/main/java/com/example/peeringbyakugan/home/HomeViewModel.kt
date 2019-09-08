package com.example.peeringbyakugan.home

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent
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

    @Inject
    lateinit var jikanIO: Jikan

    @Inject
    lateinit var cm: ConnectivityManager



    init {
        appComponent.inject(this)
        _animeRetrievalAttemptCompleted.value = true
    }


    fun queryJikanSearchAndFilter(query: String, genreList: String) {
        scope.launch {
            try {
                val response: SearchOnlyResponse = jikanIO.getAnimeListAsync(query, genreList).await()
                val list: List<SearchOnlyResultsItem?>? = response.results
                _currentAnimeList.value = list as List<SearchOnlyResultsItem>?
                _animeRetrievalSuccessful.value = true
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
                if (day == "Monday") {
                    list = response.monday
                } else if (day == "Tuesday") {
                    list = response.tuesday
                } else if (day == "Wednesday") {
                    list = response.wednesday
                } else if (day == "Thursday") {
                    list = response.thursday
                } else if (day == "Friday") {
                    list = response.friday
                } else if (day == "Saturday") {
                    list = response.saturday
                } else if (day == "Sunday") {
                    list = response.sunday
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


    companion object {

        val daysList = listOf("None", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

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