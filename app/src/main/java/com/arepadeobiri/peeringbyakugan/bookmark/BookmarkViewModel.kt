@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.arepadeobiri.peeringbyakugan.bookmark

import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import com.arepadeobiri.peeringbyakugan.AnimeRepository
import com.arepadeobiri.peeringbyakugan.daggerUtil.AppComponent
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