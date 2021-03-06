/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.arepadeobiri.peeringbyakugan.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarkAnimeDao {
    @Query("select * from databaseanime WHERE dataType = :dataType ORDER BY timeAdded ASC")
    fun getAnime(dataType: String): LiveData<List<DatabaseAnime>>

    @Query("select * from databaseanime WHERE dataType = :dataType ORDER BY timeAdded ASC")
    fun getAnimeListInstance(dataType: String): List<DatabaseAnime>


    @Query("select * from databaseanime where dataType = :dataType AND malId = :malId ")
    fun getSpecificAnime(dataType: String, malId: Int) : DatabaseAnime

    @Delete
    fun delete(anime: DatabaseAnime)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg anime: DatabaseAnime)
}

@Database(entities = [DatabaseAnime::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract val animeDao: BookmarkAnimeDao
}

private lateinit var INSTANCE: AnimeDatabase

fun getDatabase(context: Context): AnimeDatabase {
    synchronized(AnimeDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AnimeDatabase::class.java,
                "anime"
            ).build()
        }
    }
    return INSTANCE
}
