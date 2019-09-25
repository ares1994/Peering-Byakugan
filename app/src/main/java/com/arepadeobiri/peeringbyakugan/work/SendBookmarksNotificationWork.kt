package com.arepadeobiri.peeringbyakugan.work

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.arepadeobiri.peeringbyakugan.MainActivity
import com.arepadeobiri.peeringbyakugan.R
import com.arepadeobiri.peeringbyakugan.Util
import com.arepadeobiri.peeringbyakugan.Util.Companion.BOOKMARK_TYPE
import com.arepadeobiri.peeringbyakugan.database.DatabaseAnime
import com.arepadeobiri.peeringbyakugan.database.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class BookmarkNotificationWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "BookmarkNotificationWorker"
    }


    override suspend fun doWork(): Payload {
        val animeDao = getDatabase(applicationContext).animeDao

        Log.d("Ares", "Worker called ")
        return try {
            var bookmarks: List<DatabaseAnime>? = null
            withContext(Dispatchers.IO) {
                bookmarks = animeDao.getAnimeListInstance(BOOKMARK_TYPE)
            }
            val todaysBookmarks = bookmarks?.filter {
                Util.getDay(it.airingStart) == Util.getDayFromLong(System.currentTimeMillis())
            }

            if (todaysBookmarks.isNullOrEmpty()) return Payload(Result.SUCCESS)
            var string = "Heyyy! Don't forget: \n"
            todaysBookmarks.forEach {
                string += it.title + "\n"
            }
            string += if (todaysBookmarks.size == 1) "is airing today" else "are airing today"

            val intent = Intent(applicationContext, MainActivity::class.java)


            val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)


            val builder = NotificationCompat.Builder(applicationContext, Util.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bookmark_24dp)
                .setContentTitle("Anime Reminders")
                .setContentText(string)
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(string)
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(Util.notificationId, builder.build())
            }

            Payload(Result.SUCCESS)

        } catch (e: Exception) {
            Payload(Result.RETRY)
        }


    }


}