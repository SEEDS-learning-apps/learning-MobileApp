package com.example.chat_bot.data

import java.io.Serializable

data class Exercise(
                    var subjectName:String,
                    var topicName: String,
                    var obtainedscore: String,
                    var totalscore:String,
                    var time: String,
                    var question:String,
                    var answer: String
                    ): Serializable
