package com.example.chat_bot.data

import java.io.Serializable

data class trufalses(
    val `data`: List<Data>,
    val message: String,
    val success: Boolean
) : Serializable