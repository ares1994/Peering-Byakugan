package com.example.peeringbyakugan.daggerUtil

import android.content.Context
import android.net.ConnectivityManager
import com.example.peeringbyakugan.ByakuganApplication
import com.example.peeringbyakugan.network.Jikan
import com.example.peeringbyakugan.network.Network.jikanIO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: ByakuganApplication) {

    @Provides
    @Singleton
    fun networkInterface(): Jikan {
        return jikanIO
    }

    @Provides
    @Singleton
    fun getConnectivityManager(): ConnectivityManager {

        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


}