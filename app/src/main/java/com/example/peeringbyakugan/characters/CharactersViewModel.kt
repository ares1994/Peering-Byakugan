package com.example.peeringbyakugan.characters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.network.charactersDataModels.CharactersItem
import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
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


}