package com.example.chat_bot.data

data class Quizz(
    var _id: String,
    var topic : String,
    var ageGroup: String,
    var language: String,
    var country: String,
    var grade: String,
    var noOfQuestions: String,
    var time: String,
    var userId: String,
    var subId: String,
    var ageId: String,
    var __v: String,
    val `allQuestions`: List<AllQuestion>,
)
