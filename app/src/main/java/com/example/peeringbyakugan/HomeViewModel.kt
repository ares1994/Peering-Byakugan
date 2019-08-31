package com.example.peeringbyakugan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.DaggerUtil.AppModule
import com.example.peeringbyakugan.DaggerUtil.DaggerAppComponent
import com.example.peeringbyakugan.Network.Jikan
import com.example.peeringbyakugan.Network.SearchOnlyResponse
import com.example.peeringbyakugan.Network.SearchOnlyResultsItem
import kotlinx.coroutines.*
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    @Inject lateinit var jikanIO: Jikan

    private val appComponent = DaggerAppComponent.create()

    init {
        appComponent.inject(this)
    }


    fun networkTest() {
        scope.launch {
            try {
                val response: SearchOnlyResponse =  jikanIO.getAnimeUsingOnlySearch("bleac").await()
                val list: List<SearchOnlyResultsItem?>? = response.results
                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.title}")
                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.synopsis}")

            } catch (t: Throwable) {
                Log.d("HomeViewModel", "${t.message}")
            }
        }
    }


}