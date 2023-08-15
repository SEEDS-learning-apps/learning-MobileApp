package com.example.chat_bot.data

data class Mcqs(
    val __v: Int,
    val _id: String,
    var answer: String,
    val mcqs: String,
    var option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val posFeedback: String,
    val negFeedback: String,
    val sequence: String,
    val userId: String,
    var topicId: String
)