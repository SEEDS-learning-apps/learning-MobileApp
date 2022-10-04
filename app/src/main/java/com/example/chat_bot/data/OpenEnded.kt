package com.example.chat_bot.data

import java.io.Serializable
import java.security.Timestamp

data class OpenEnded(

    val questionId: String,
    var timeStamp: String,
    val answer: String,
    val username:String,
    val userId:String

 ) : Serializable
