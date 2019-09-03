package com.example.peeringbyakugan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.DaggerAppComponent
import com.example.peeringbyakugan.detailsData.SingleAnimeResponse
import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.SearchOnlyResponse
import com.example.peeringbyakugan.network.SearchOnlyResultsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _currentAnime = MutableLiveData<SingleAnimeResponse>()
    val currentAnime: LiveData<SingleAnimeResponse> get() = _currentAnime

    private val appComponent = DaggerAppComponent.create()

    @Inject
    lateinit var jikanIO: Jikan

    init {
        appComponent.inject(this)

    }


    fun queryJikanForAnime(animeId: Int) {
        Log.d("DetailsViewModel", "QueryJikanForAnime called")
        scope.launch {
            try {
                _currentAnime.value = jikanIO.getAnimeAsync(animeId).await()
            } catch (t: Throwable) {
                Log.d("DetailsViewModel", "${t.message}")

            }
        }


    }
}