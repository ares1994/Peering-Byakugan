package com.example.peeringbyakugan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peeringbyakugan.bookmark.BookmarkViewModel
import com.example.peeringbyakugan.characterDetails.CharacterDetailsViewModel
import com.example.peeringbyakugan.characters.CharactersViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.details.DetailsViewModel
import com.example.peeringbyakugan.home.HomeViewModel

class GenericViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(appComponent) as T
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> DetailsViewModel(
                appComponent
            ) as T
            modelClass.isAssignableFrom(CharactersViewModel::class.java) -> CharactersViewModel(
                appComponent
            ) as T
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> BookmarkViewModel(
                appComponent
            ) as T
            modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java) -> CharacterDetailsViewModel(
                appComponent
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel Class")
        }
    }
}