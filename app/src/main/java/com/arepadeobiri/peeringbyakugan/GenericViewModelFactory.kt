package com.arepadeobiri.peeringbyakugan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arepadeobiri.peeringbyakugan.bookmark.BookmarkViewModel
import com.arepadeobiri.peeringbyakugan.characterDetails.CharacterDetailsViewModel
import com.arepadeobiri.peeringbyakugan.characters.CharactersViewModel
import com.arepadeobiri.peeringbyakugan.daggerUtil.AppComponent
import com.arepadeobiri.peeringbyakugan.details.DetailsViewModel
import com.arepadeobiri.peeringbyakugan.favourites.FavouritesViewModel
import com.arepadeobiri.peeringbyakugan.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
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
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> FavouritesViewModel(
                appComponent
            ) as T
            else -> throw IllegalArgumentException("Unknown viewModel Class")
        }
    }
}