package com.example.peeringbyakugan.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.details.DetailsViewModel

class CharactersViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(appComponent) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}