package com.example.chat_bot.data

import java.io.Serializable

data class Data(
    val __v: Int,
    val _id: String,
    val answer: String,
    val question: String,
    val topicId: String,
    val sequence: String,
    val userId: String,
) : Serializable