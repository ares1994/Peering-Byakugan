package com.example.peeringbyakugan.daggerUtil

import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.Network.jikanIO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides @Singleton
    fun networkInterface(): Jikan{
        return jikanIO
    }


}