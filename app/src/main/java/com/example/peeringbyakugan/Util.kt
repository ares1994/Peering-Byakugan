package com.example.peeringbyakugan

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Util {

    companion object {
        const val CHANNEL_ID = "reminder_notification"
        const val notificationId = 100

        fun getDay(date: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val dateObject = inputFormat.parse(date.split("\\+".toRegex())[0])

            val outputFormat = SimpleDateFormat("EEEE")

            return outputFormat.format(dateObject!!)
        }

        fun getTime(date: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val dateObject = inputFormat.parse(date.split("\\+".toRegex())[0])

            val outputFormat = SimpleDateFormat("HH:mm",Locale.getDefault())

            return outputFormat.format(dateObject!!)
        }

        fun getDayFromLong(time: Long): String {
            val dateFormat = SimpleDateFormat("EEEE",
                Locale.getDefault())

            return dateFormat.format(time).toString()
        }




    }
}