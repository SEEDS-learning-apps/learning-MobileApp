package com.example.chat_bot.data

import java.io.Serializable

data class Topics(

    val _id: String,
    var topic: String,
    val ageGroup: String,
    val language: String,
    val country: String,
    val grade: String,
    val noOfQuestions: String,
    val time: String,
    val userId:String,
    val subId: String,
    val ageId: String,
 ) : Serializable
