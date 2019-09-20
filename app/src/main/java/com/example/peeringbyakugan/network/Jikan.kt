package com.example.peeringbyakugan.network

import com.example.peeringbyakugan.network.charactersDataModels.CharactersResponse
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
        @Query("score") score: String,
        @Query("order_by") orderBy : String
    ): Deferred<SearchOnlyResponse>


    @GET("anime/{animeId}")
    fun getAnimeAsync(
        @Path("animeId") animeId: Int
    ): Deferred<SingleAnimeResponse>

    @GET("anime/{animeId}/characters_staff")
    fun getAnimeCharactersAsync(
        @Path("animeId") animeId: Int
    ): Deferred<CharactersResponse>


    @GET("schedule/{day}?type=anime")
    fun getScheduleAsync(
        @Path("day") day: String
    ): Deferred<ScheduleResponse>
}


/**
 * Main entry point for network access. Call like `Network.jikanIO.getAnimeListAsync`
 */

