package com.example.chat_bot.Activities.Notification.Activity.Notification_data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications

@Dao
interface NotificationDao {

    // this method  inserts alarm items into room database
    @Insert
    suspend fun insert(alarm: Notifications)

    // this method  update an inserted alarm items into room database
    @Update
    suspend fun update(alarm: Notifications)

    // this method  deletes alarm items into room database
    @Delete
    suspend fun delete(alarm: Notifications)

    // this method  deletes all alarm items from room database
    @Query("DELETE FROM alarms_items")
    suspend fun deleteAllAlarms()

    // this method gets all alarm items from room database
    @Query("SELECT * FROM alarms_items")
    fun getAllAlarms():LiveData<List<Notifications>>
}