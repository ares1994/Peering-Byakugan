package com.example.peeringbyakugan

import android.util.Log
import com.example.peeringbyakugan.database.BookmarkAnimeDao
import com.example.peeringbyakugan.database.DatabaseAnime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AnimeRepository (private val animeDao: BookmarkAnimeDao){


    val animeList = animeDao.getAnime()



    suspend fun saveBookmarkedAnime(anime: DatabaseAnime){
        withContext(Dispatchers.IO){
            animeDao.insert(anime)
            Log.d("Ares", "Anime Inserted")
        }
    }
}