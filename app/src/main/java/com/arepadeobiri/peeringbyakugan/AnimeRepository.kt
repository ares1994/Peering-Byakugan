package com.arepadeobiri.peeringbyakugan

import android.content.SharedPreferences
import android.util.Log
import com.arepadeobiri.peeringbyakugan.Util.Companion.BOOKMARK_TYPE
import com.arepadeobiri.peeringbyakugan.Util.Companion.DATABASE_RESPONSE
import com.arepadeobiri.peeringbyakugan.Util.Companion.FAVOURITE_TYPE
import com.arepadeobiri.peeringbyakugan.database.BookmarkAnimeDao
import com.arepadeobiri.peeringbyakugan.database.DatabaseAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository (private val animeDao: BookmarkAnimeDao, private val pref: SharedPreferences){


    val bookmarkAnimeList = animeDao.getAnime(BOOKMARK_TYPE)

    val favouritesAnimeList = animeDao.getAnime(FAVOURITE_TYPE)





    suspend fun saveDatabaseAnime(anime: DatabaseAnime){
        withContext(Dispatchers.IO){
            val retrieved : DatabaseAnime? = animeDao.getSpecificAnime(anime.dataType, anime.malId)
            if(retrieved == null){
                animeDao.insert(anime)
                pref.edit().putString(DATABASE_RESPONSE,"Saved to ${anime.dataType}").apply()
            } else{
                pref.edit().putString(DATABASE_RESPONSE, "Already in ${anime.dataType}").apply()
            }
        }
    }

    suspend fun removeAnime(anime: DatabaseAnime){
        withContext(Dispatchers.IO){
            animeDao.delete(anime)
            Log.d("Ares", "Anime deleted")
        }
    }
}