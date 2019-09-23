package com.example.peeringbyakugan

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.work.*
import com.example.peeringbyakugan.daggerUtil.AppComponent
import com.example.peeringbyakugan.daggerUtil.AppModule
import com.example.peeringbyakugan.daggerUtil.DaggerAppComponent
import com.example.peeringbyakugan.work.BookmarkNotificationWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class ByakuganApplication : Application() {

    private lateinit var appComponent: AppComponent

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        providesNotificationChannel()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        delayedInit()


    }


    fun getAppComponent(): AppComponent {
        return appComponent
    }


    private fun delayedInit(){
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {


        val repeatingRequest
                = PeriodicWorkRequestBuilder<BookmarkNotificationWorker>(1, TimeUnit.DAYS,12,TimeUnit.HOURS)
            .build()


        WorkManager.getInstance().enqueueUniquePeriodicWork(
            BookmarkNotificationWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeatingRequest)

    }

    private fun providesNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Util.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

}