package com.example.chat_bot.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    val username: String,
    val age: String,
    val country: String,
    val grade: String,
    val language: String,
    val dev_id: String,
    val preferredmaterialLanguage: String
)




