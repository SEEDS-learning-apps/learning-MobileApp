package com.example.chat_bot.Room.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.chat_bot.data.User
import com.example.chat_bot.data.QuestItem


data class UserAndMaterials (
    @Embedded val user: User,

    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val downloadedQuiz: List<QuestItem>
)