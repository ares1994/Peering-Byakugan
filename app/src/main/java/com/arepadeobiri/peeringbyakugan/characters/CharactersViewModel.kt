package com.arepadeobiri.peeringbyakugan.characters

import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arepadeobiri.peeringbyakugan.daggerUtil.AppComponent
import com.arepadeobiri.peeringbyakugan.network.charactersDataModels.CharactersItem
import com.arepadeobiri.peeringbyakugan.network.Jikan
import com.arepadeobiri.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersViewModel(appComponent: AppComponent) : ViewModel() {

    private val job = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private val _currentAnimeCharacterList = MutableLiveData<List<SearchOnlyResultsItem?>?>()
    val currentAnimeCharacterList: LiveData<List<SearchOnlyResultsItem?>?> get() = _currentAnimeCharacterList

    @Inject
    lateinit var jikanIO: Jikan

    @Inject lateinit var cm : ConnectivityManager

    init {
        appComponent.inject(this)
    }

    fun queryJikanForAnimeCharacters(animeId: Int) {
        uiScope.launch {
            try {
                val response = jikanIO.getAnimeCharactersAsync(animeId).await()
                _currentAnimeCharacterList.value = response.characters.convertToSearchItem()

            } catch (t: Throwable) {
                Log.d("CharactersViewModel", "${t.message}")
                _currentAnimeCharacterList.value = null

            }
        }

    }


    private fun List<CharactersItem?>?.convertToSearchItem(): List<SearchOnlyResultsItem?>? {
        return this?.map {
            SearchOnlyResultsItem(
                null, it?.imageUrl, it?.malId, null, it?.name, null,
                null, null, null, null, false, null, null
            )

        }


    }


    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }


}