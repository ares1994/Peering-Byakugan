package com.arepadeobiri.peeringbyakugan.network

import com.arepadeobiri.peeringbyakugan.network.characterDetailDataModels.CharacterDetailsResponse
import com.arepadeobiri.peeringbyakugan.network.charactersDataModels.CharactersResponse
import com.arepadeobiri.peeringbyakugan.network.singleAnimeDataModels.SingleAnimeResponse
import com.arepadeobiri.peeringbyakugan.network.scheduleDataModels.ScheduleResponse
import com.arepadeobiri.peeringbyakugan.network.searchDataModels.SearchOnlyResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Jikan {
    @GET("search/anime")
    fun getAnimeListAsync(
        @Query("q") query: String,
        @Query("genre") genre: String,
        @Query("score") score: String,
        @Query("order_by") orderBy : String,
        @Query("page") page: Int
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


    @GET("character/{characterId}")
    fun getCharacterDetailsAsync(
        @Path("characterId") characterId : Int
    ): Deferred<CharacterDetailsResponse>
}


/**
 * Main entry point for network access. Call like `Network.jikanIO.getAnimeListAsync`
 */

