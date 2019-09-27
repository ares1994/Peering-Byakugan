package com.arepadeobiri.peeringbyakugan.daggerUtil

import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.arepadeobiri.peeringbyakugan.AnimeRepository
import com.arepadeobiri.peeringbyakugan.ByakuganApplication
import com.arepadeobiri.peeringbyakugan.Util.Companion.PREF_NAME
import com.arepadeobiri.peeringbyakugan.database.AnimeDatabase
import com.arepadeobiri.peeringbyakugan.database.BookmarkAnimeDao
import com.arepadeobiri.peeringbyakugan.database.getDatabase
import com.arepadeobiri.peeringbyakugan.network.Jikan
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val application: ByakuganApplication) {



    @Provides
    @Singleton
    fun getConnectivityManager(): ConnectivityManager {

        return application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun getNotificationManager(): NotificationManager{
        return  application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun getApplicationContext(): ByakuganApplication{
        return application
    }

    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideJikan(retrofit: Retrofit) : Jikan{
        return retrofit.create(Jikan::class.java)
    }

    @Provides
    fun getAnimeDatabase(): AnimeDatabase{
        return getDatabase(application)
    }

    @Provides
    fun getAnimeDao(animeDatabase: AnimeDatabase): BookmarkAnimeDao{
        return animeDatabase.animeDao
    }

    @Provides
    @Singleton
    fun getSharedPreferences(): SharedPreferences {
        return application.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun getAnimeRepo(pref: SharedPreferences, animeDao : BookmarkAnimeDao): AnimeRepository{
        return AnimeRepository(animeDao, pref)
    }

    @Provides
    @Singleton
    fun getPicasso(): Picasso{
        return Picasso.get()
    }





}