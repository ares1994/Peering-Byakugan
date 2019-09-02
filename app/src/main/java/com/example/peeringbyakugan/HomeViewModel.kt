package com.example.peeringbyakugan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.DaggerAppComponent
import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.SearchOnlyResponse
import com.example.peeringbyakugan.network.SearchOnlyResultsItem
import kotlinx.coroutines.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _currentAnimeList = MutableLiveData<List<SearchOnlyResultsItem>>()
    val currentAnimeList: MutableLiveData<List<SearchOnlyResultsItem>> get() = _currentAnimeList

    private val _animeRetrievalAttemptCompleted = MutableLiveData<Boolean>()
    val animeRetrievalAttemptCompleted : LiveData<Boolean> get() = _animeRetrievalAttemptCompleted

    @Inject lateinit var jikanIO: Jikan

    private val appComponent = DaggerAppComponent.create()

    init {
        appComponent.inject(this)
        _animeRetrievalAttemptCompleted.value = true
    }


    fun queryJikanSearchAndFilter(query: String, genreList: String) {
        scope.launch {
            try {
                val response: SearchOnlyResponse =  jikanIO.getAnimeAsync(query, genreList).await()
                val list: List<SearchOnlyResultsItem?>? = response.results
                _currentAnimeList.value = list as List<SearchOnlyResultsItem>?
//                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.title}")
//                Log.d("HomeViewModel", "And it's synopsis is: ${list?.get(0)?.synopsis}")

            } catch (t: Throwable) {
                Log.d("HomeViewModel", "${t.message}")
                progressBarInvisible()
            }
        }
    }

    fun progressBarVisible(){
        _animeRetrievalAttemptCompleted.value = false
    }


    fun progressBarInvisible(){
        _animeRetrievalAttemptCompleted.value = true
    }




}