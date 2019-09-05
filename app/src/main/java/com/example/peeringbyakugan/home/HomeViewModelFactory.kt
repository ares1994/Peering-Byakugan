package com.example.peeringbyakugan.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peeringbyakugan.daggerUtil.AppComponent

class HomeViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appComponent) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}