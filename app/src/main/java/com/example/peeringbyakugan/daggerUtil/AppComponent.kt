package com.example.peeringbyakugan.daggerUtil

import com.example.peeringbyakugan.DetailsViewModel
import com.example.peeringbyakugan.HomeViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewModel: HomeViewModel)

    fun inject(viewModel: DetailsViewModel)

}
