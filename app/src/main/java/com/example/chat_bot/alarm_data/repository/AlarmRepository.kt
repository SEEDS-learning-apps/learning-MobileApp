package com.crushtech.timelyapp.data.repository

import com.example.chat_bot.alarm_data.database.AlarmDatabase
import com.example.chat_bot.alarm_data.entities.Alarms


class AlarmRepository(private val db: AlarmDatabase) {
    suspend fun insert(alarm: Alarms) = db.getAlarmDao().insert(alarm)
    suspend fun update(alarm: Alarms) = db.getAlarmDao().update(alarm)
    suspend fun delete(alarm: Alarms) = db.getAlarmDao().delete(alarm)
    suspend fun deleteAllAlarms() = db.getAlarmDao().deleteAllAlarms()
    fun getAllAlarms() = db.getAlarmDao().getAllAlarms()
}