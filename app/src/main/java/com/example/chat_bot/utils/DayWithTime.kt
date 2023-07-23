package com.example.chat_bot.utils

import android.annotation.SuppressLint
import android.text.format.DateUtils
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object DayWithTime {
    @SuppressLint("SimpleDateFormat")
    fun timeStamp(): String {
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))

        val currentTimeMillis = System.currentTimeMillis()
        val dateTimeMillis = timeStamp.time

        return if (DateUtils.isToday(dateTimeMillis)) {
            time // Display only time for today
        } else {
            val timeSpanString = when {
                DateUtils.isToday(dateTimeMillis + DateUtils.DAY_IN_MILLIS) -> "Yesterday"
                else -> DateUtils.getRelativeTimeSpanString(dateTimeMillis, currentTimeMillis, DateUtils.DAY_IN_MILLIS).toString()
            }
            "$timeSpanString, $time" // Display the relative time and time for other days
        }
    }
}