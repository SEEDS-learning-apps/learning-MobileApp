package com.example.chat_bot.Rasa.rasaMsg


data class BotResponse(
    var recipient_id: String,
    var text: String,
    var image: String,
    var buttons: List<Button>
)

data class Button(
    val payload: String,
    val title: String
)
