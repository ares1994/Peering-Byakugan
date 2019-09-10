package com.example.peeringbyakugan.network

import com.example.peeringbyakugan.network.singleAnimeDataModels.SingleAnimeResponse
import com.example.peeringbyakugan.network.scheduleDataModels.ScheduleResponse
import com.example.peeringbyakugan.network.searchDataModels.SearchOnlyResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Jikan {
    @GET("search/anime")
    fun getAnimeListAsync(
        @Query("q") query: String,
        @Query("genre") genre: String,
        @Query("score") score: String
    ): Deferred<SearchOnlyResponse>


    @GET("anime/{animeId}")
    fun getAnimeAsync(
        @Path("animeId") animeId: Int
    ): Deferred<SingleAnimeResponse>


    @GET("schedule/{day}?type=anime")
    fun getScheduleAsync(
        @Path("day") day: String
    ): Deferred<ScheduleResponse>
}


/**
 * Main entry point for network access. Call like `Network.jikanIO.getAnimeListAsync`
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
