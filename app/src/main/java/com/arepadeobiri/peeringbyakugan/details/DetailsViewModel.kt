@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.arepadeobiri.peeringbyakugan.details

import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arepadeobiri.peeringbyakugan.AnimeRepository
import com.arepadeobiri.peeringbyakugan.Util.Companion.BOOKMARK_TYPE
import com.arepadeobiri.peeringbyakugan.Util.Companion.FAVOURITE_TYPE
import com.arepadeobiri.peeringbyakugan.daggerUtil.AppComponent
import com.arepadeobiri.peeringbyakugan.database.DatabaseAnime
import com.arepadeobiri.peeringbyakugan.network.singleAnimeDataModels.SingleAnimeResponse
import com.arepadeobiri.peeringbyakugan.network.Jikan
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

    private val _currentResponse = MutableLiveData<String>()
    val currentResponse: LiveData<String> get() = _currentResponse


    @Inject
    lateinit var jikanIO: Jikan

    @Inject
    lateinit var cm: ConnectivityManager

    @Inject
    lateinit var picasso: Picasso

    @Inject
    lateinit var animeRepo: AnimeRepository

    @Inject
    lateinit var pref: SharedPreferences


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




    fun favouriteAnimeToDatabase() {
        scope.launch {
            if (_currentAnime.value != null) {
                val anime = _currentAnime.value!!
                animeRepo.saveDatabaseAnime(
                    DatabaseAnime(
                        anime.malId!!,
                        anime.title!!,
                        anime.airing!!,
                        anime.aired!!.from!!,
                        anime.imageUrl!!,
                        FAVOURITE_TYPE,
                        System.currentTimeMillis()
                    )
                )

                _currentResponse.value = pref.getString("response","")
            }

        }
    }

    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }
}