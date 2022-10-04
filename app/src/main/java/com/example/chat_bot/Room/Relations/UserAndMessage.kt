package com.example.chat_bot.Room.Relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.chat_bot.data.Message
import com.example.chat_bot.data.R_Message
import com.example.chat_bot.data.User

data class UserAndMessage(

    @Embedded val user: User,
    @Relation(
        parentColumn = "username",
        entityColumn = "username",
    )
     val message: List<R_Message>

)