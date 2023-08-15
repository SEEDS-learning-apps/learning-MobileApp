package com.example.chat_bot.Activities.Notification.Activity.Notification_data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.dao.NotificationDao
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications


@Database(entities = [Notifications::class], version = 1, exportSchema = false)
abstract class NotificationDatabase : RoomDatabase() {

    //this method would be called in our alarm repos
    abstract fun getAlarmDao(): NotificationDao


    companion object {
        private var instance: NotificationDatabase? = null
        private val lock = Any()

        //this method is called every time this class is invoked
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createDatabase(context).also { instance = it }
        }


        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context,
            NotificationDatabase::class.java, "alarmDb.db"
        ).fallbackToDestructiveMigration().build()
    }
}

