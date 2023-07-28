package com.example.chat_bot.data

import java.io.Serializable

data class Question(
    var question: String? = null,
    var answer: String? = null,
    var statment1: String? = null,
    var answer1: String? = null,
    var statment2: String? = null,
    var answer2: String? = null,
    var statment3: String? = null,
    var answer3: String? = null,
    var statment4: String? = null,
    var answer4: String? = null,
    var statment5: String? = null,
    var answer5: String? = null,
    val link: String?,
    val file: String?
): Serializable

data class Exercise(
    var questionType: String? = null,
    var quizSize: Int,
    var subjectName: String = "",
    var topicName: String = "",
    var obtainedscore: String = "",
    var totalscore: String = "",
    var time: String = "",
    var questions: MutableList<Question>,
):Serializable

