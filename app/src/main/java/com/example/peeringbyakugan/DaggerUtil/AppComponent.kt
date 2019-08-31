package com.example.peeringbyakugan.DaggerUtil

import com.example.peeringbyakugan.HomeViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(viewModel: HomeViewModel)

}
