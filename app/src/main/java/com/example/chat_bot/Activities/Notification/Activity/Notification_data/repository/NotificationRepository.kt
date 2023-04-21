package com.crushtech.timelyapp.data.repository

import com.example.chat_bot.Activities.Notification.Activity.Notification_data.database.NotificationDatabase
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications


class NotificationRepository(private val db: NotificationDatabase) {
    suspend fun insert(alarm: Notifications) = db.getAlarmDao().insert(alarm)
    suspend fun update(alarm: Notifications) = db.getAlarmDao().update(alarm)
    suspend fun delete(alarm: Notifications) = db.getAlarmDao().delete(alarm)
    suspend fun deleteAllAlarms() = db.getAlarmDao().deleteAllAlarms()
    fun getAllAlarms() = db.getAlarmDao().getAllAlarms()
}