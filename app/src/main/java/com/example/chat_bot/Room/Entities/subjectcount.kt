package com.example.chat_bot.Room.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject_counts")
data class SubjectCount(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val subject: String,
    val count: Int
)
