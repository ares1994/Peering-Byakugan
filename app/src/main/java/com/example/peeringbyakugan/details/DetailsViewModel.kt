@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.peeringbyakugan.details

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.network.singleAnimeDataModels.SingleAnimeResponse
import com.example.peeringbyakugan.network.Jikan
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
class DetailsViewModel(appComponent: AppComponent) : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _currentAnime = MutableLiveData<SingleAnimeResponse>()
    val currentAnime: LiveData<SingleAnimeResponse> get() = _currentAnime


    @Inject
    lateinit var jikanIO: Jikan

    @Inject lateinit var cm: ConnectivityManager

    @Inject lateinit var picasso: Picasso

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
                _currentAnime.value = null
            }
        }
    }

    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }
}