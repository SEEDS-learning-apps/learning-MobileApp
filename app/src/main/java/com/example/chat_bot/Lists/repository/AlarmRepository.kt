package com.crushtech.timelyapp.data.repository

import com.example.chat_bot.Room.Entities.Alarms
import com.example.chat_bot.Room.SeedsDatabase


class AlarmRepository(private val db: SeedsDatabase) {
    suspend fun insert(alarm: Alarms) = db.seedsDao.insert(alarm)
    suspend fun update(alarm: Alarms) = db.seedsDao.update(alarm)
    suspend fun delete(alarm: Alarms) = db.seedsDao.delete(alarm)
    suspend fun deleteAllAlarms() = db.seedsDao.deleteAllAlarms()
    fun getAllAlarms() = db.seedsDao.getAllAlarms()
}