package com.example.chat_bot.Room.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.chat_bot.data.User
import com.example.chat_bot.data.tryy.DowloadedQuiz
import com.example.chat_bot.data.tryy.QuestItem


data class UserAndMaterials (
    @Embedded val user: User,

    @Relation(
        parentColumn = "username",
        entityColumn = "username"
    )
    val downloadedQuiz: List<QuestItem>
)