package com.example.peeringbyakugan

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.DaggerUtil.AppModule
import com.example.peeringbyakugan.DaggerUtil.DaggerAppComponent
import com.example.peeringbyakugan.Network.Jikan
import com.example.peeringbyakugan.Network.SearchOnlyResponse
import com.example.peeringbyakugan.Network.SearchOnlyResultsItem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class HomeViewModel : ViewModel() {

    private var viewModelJob = Job()
    private var scope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _currentAnimeList = MutableLiveData<List<SearchOnlyResultsItem>>()
    val currentAnimeList: MutableLiveData<List<SearchOnlyResultsItem>> get() = _currentAnimeList

    @Inject lateinit var jikanIO: Jikan

    private val appComponent = DaggerAppComponent.create()

    init {
        appComponent.inject(this)
    }


    fun queryJikanSearchOnly(query: String) {
        scope.launch {
            try {
                val response: SearchOnlyResponse =  jikanIO.getAnimeUsingOnlySearch(query).await()
                val list: List<SearchOnlyResultsItem?>? = response.results
                _currentAnimeList.value = list as List<SearchOnlyResultsItem>?
//                Log.d("HomeViewModel", "The name of the first anime is ${list?.get(0)?.title}")
//                Log.d("HomeViewModel", "And it's synopsis is: ${list?.get(0)?.synopsis}")

            } catch (t: Throwable) {
                Log.d("HomeViewModel", "${t.message}")
            }
        }
    }


}