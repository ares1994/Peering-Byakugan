package com.example.peeringbyakugan.details



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.peeringbyakugan.daggerUtil.AppComponent

class DetailsViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(appComponent) as T
        }
        throw IllegalArgumentException("Unknown viewModel Class")
    }
}