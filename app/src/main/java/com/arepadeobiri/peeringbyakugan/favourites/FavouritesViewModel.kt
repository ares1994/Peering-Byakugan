package com.arepadeobiri.peeringbyakugan.favourites

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.arepadeobiri.peeringbyakugan.AnimeRepository
import com.arepadeobiri.peeringbyakugan.daggerUtil.AppComponent
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

