package com.example.peeringbyakugan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.Network.Network.jikanIO
import com.example.peeringbyakugan.Network.SearchOnlyResponse
import com.example.peeringbyakugan.Network.SearchOnlyResultsItem
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)


//    fun networkTest() {
//        scope.launch {
//            try {
//                val response: SearchOnlyResponse =  jikanIO.getAnimeUsingOnlySearch("Naruto").await()
//                val list: List<SearchOnlyResultsItem?>? = response.results
//                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.title}")
//
//            } catch (t: Throwable) {
//                Log.d("HomeViewModel", "${t.message}")
//            }
//        }
//    }


}