package com.example.peeringbyakugan.favourites

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.AnimeRepository
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.daggerUtil.DaggerAppComponent
import javax.inject.Inject

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class FavouritesViewModel(appComponent: AppComponent) : ViewModel(){


    @Inject
    lateinit var animeRepo : AnimeRepository

    @Inject
    lateinit var cm: ConnectivityManager




    init {

        appComponent.inject(this)
    }


    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }
}

