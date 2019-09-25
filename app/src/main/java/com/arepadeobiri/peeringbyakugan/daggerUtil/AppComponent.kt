package com.arepadeobiri.peeringbyakugan.daggerUtil

import com.arepadeobiri.peeringbyakugan.AnimeRepository
import com.arepadeobiri.peeringbyakugan.bookmark.BookmarkViewModel
import com.arepadeobiri.peeringbyakugan.characterDetails.CharacterDetailsViewModel
import com.arepadeobiri.peeringbyakugan.characters.CharactersViewModel
import com.arepadeobiri.peeringbyakugan.details.DetailsViewModel
import com.arepadeobiri.peeringbyakugan.favourites.FavouritesViewModel
import com.arepadeobiri.peeringbyakugan.home.HomeViewModel
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

    fun inject(animeRepo: AnimeRepository)
}
