package com.example.peeringbyakugan.DaggerUtil

import com.example.peeringbyakugan.Network.Jikan
import com.example.peeringbyakugan.Network.Network
import com.example.peeringbyakugan.Network.Network.jikanIO
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