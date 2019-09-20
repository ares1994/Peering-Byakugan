package com.example.peeringbyakugan.bookmark

import androidx.lifecycle.ViewModel
import com.example.peeringbyakugan.daggerUtil.AppComponent

class BookmarkViewModel(appComponent: AppComponent) : ViewModel(){

    init {
        appComponent.inject(this)
    }



}