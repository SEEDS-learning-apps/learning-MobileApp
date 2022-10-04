package com.example.chat_bot.data

import java.io.Serializable

data class AllQuestion(
    var _id: String,
    var mcqs:String,
    var option1: String,
    var option2: String,
    var option3: String,
    var option4: String,
    var file: String,
    var answer: String,
    var posFeedback: String,
    var negFeedback: String,
    var sequence: String,
    var questionType: String,
    var userId: String,
    var topicId: String,
    var __v: String,
    var question: String,
    var introduction: String,
    var statement1: String,
    var answer1: String,
    var statement2: String,
    var answer2: String,
    var statement3: String,
    var answer3: String,
    var statement4: String,
    var answer4: String,
    var statement5: String,
    var answer5: String,
): Serializable
