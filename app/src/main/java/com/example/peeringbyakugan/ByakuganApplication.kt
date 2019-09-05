package com.example.peeringbyakugan

import android.app.Application
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.daggerUtil.AppModule
import com.example.peeringbyakugan.daggerUtil.DaggerAppComponent

class ByakuganApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }


    fun getAppComponent(): AppComponent {
        return appComponent
    }
}