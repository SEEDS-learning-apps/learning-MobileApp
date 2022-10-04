package com.example.chat_bot.data

data class Truefalse(

    val _id: String,
    val question: String,
    var answer: String,
//    val posFeedback: String,
//    val negFeedback: String,
    val userId: String,
    var topicId: String
)
