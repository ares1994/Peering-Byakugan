package com.example.peeringbyakugan.daggerUtil

import com.example.peeringbyakugan.MainActivity
import com.example.peeringbyakugan.bookmark.BookmarkViewModel
import com.example.peeringbyakugan.characterDetails.CharacterDetailsViewModel
import com.example.peeringbyakugan.characters.CharactersViewModel
import com.example.peeringbyakugan.details.DetailsViewModel
import com.example.peeringbyakugan.favourites.FavouritesViewModel
import com.example.peeringbyakugan.home.HomeFragment
import com.example.peeringbyakugan.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewModel: HomeViewModel)

    fun inject(viewModel: DetailsViewModel)

    fun inject(viewModel: CharactersViewModel)

    fun inject(viewModel: BookmarkViewModel)

    fun inject(viewModel: CharacterDetailsViewModel)

    fun inject(viewModel: FavouritesViewModel)

}
