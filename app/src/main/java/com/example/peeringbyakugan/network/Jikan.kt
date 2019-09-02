package com.example.peeringbyakugan.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Jikan {
    @GET("search/anime")
    fun getAnimeAsync(
        @Query("q") query: String,
        @Query("genre") genre: String
    ): Deferred<SearchOnlyResponse>
}



/**
 * Main entry point for network access. Call like `Network.jikanIO.getAnimeAsync`
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
