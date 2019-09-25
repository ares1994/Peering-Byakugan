package com.example.peeringbyakugan.characterDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.characterDetailDataModels.CharacterDetailsResponse
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResultsItem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel(appComponent: AppComponent) : ViewModel() {

    private val job = Job()

    private val scope = CoroutineScope(Dispatchers.Main + job)


    @Inject
    lateinit var jikanIO: Jikan

    @Inject
    lateinit var picasso: Picasso

    private val _characterDetails = MutableLiveData<CharacterDetailsResponse>()
    val characterDetails: LiveData<CharacterDetailsResponse> get() = _characterDetails


    init {
        appComponent.inject(this)
    }


    fun getCharacterDetails(characterId: Int) {
        scope.launch {

            try {
                val response = jikanIO.getCharacterDetailsAsync(characterId).await()
                _characterDetails.value = response

            } catch (t: Throwable) {
                Log.d("CharacterDetailsVM", "${t.message}")
                _characterDetails.value = null
            }

        }
    }


    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}