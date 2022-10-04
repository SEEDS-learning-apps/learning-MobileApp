package com.example.chat_bot.Room.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Data(
    val __v: Int,
    val _id: String,
    val age: String,
    val country: String,
    val dev_id: String,
    val grade: String,
    val language: String,
    @PrimaryKey(autoGenerate = false)
    val name: String
)