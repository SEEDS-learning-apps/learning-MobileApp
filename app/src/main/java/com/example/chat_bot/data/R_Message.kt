package com.example.chat_bot.data

import androidx.room.*
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Room.Coverters.ButtonConverter

@Entity
data class R_Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val message:String,
    val msgId:String,
    val time: String,
    val has_suggestion: Boolean,
    var topic: String,
    @TypeConverters(ButtonConverter::class)
    var buttons: List<com.example.chat_bot.Rasa.rasaMsg.Button>,
    val username: String,
    )