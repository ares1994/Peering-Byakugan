package com.example.peeringbyakugan.Network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Jikan {
    @GET("search/anime")
    fun getAnimeUsingOnlySearch(
        @Query("q") query: String
    ): Deferred<SearchOnlyResponse>
}



/**
 * Main entry point for network access. Call like `Network.jikanIO.getAnimeUsingOnlySearch`
 */
object Network {
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val jikanIO: Jikan = retrofit.create(Jikan::class.java)
}
