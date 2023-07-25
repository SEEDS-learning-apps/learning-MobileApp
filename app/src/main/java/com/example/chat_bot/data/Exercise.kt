package com.example.chat_bot.data

import java.io.Serializable

data class Exercise(
    var questionType: String?,
    var subjectName:String,
    var topicName: String,
    var obtainedscore: String,
    var totalscore:String,
    var time: String,
    var question: String?,
    var answer: String?,
    var statment1: String?,
    var answer1: String?,
    var statment2: String?,
    var answer2: String?,
    var statment3: String?,
    var answer3: String?,
    var statment4: String?,
    var answer4: String?

                    ): Serializable
