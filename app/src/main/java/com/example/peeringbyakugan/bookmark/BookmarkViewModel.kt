@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.peeringbyakugan.bookmark

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.AnimeRepository
import com.example.peeringbyakugan.daggerUtil.AppComponent
import javax.inject.Inject

@Suppress("DEPRECATION")
class BookmarkViewModel(appComponent: AppComponent) : ViewModel() {
    @Inject
    lateinit var cm: ConnectivityManager

    @Inject
    lateinit var animeRepo: AnimeRepository


    init {
        appComponent.inject(this)
    }


    fun isInternetConnection(): Boolean {

        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

    }


}