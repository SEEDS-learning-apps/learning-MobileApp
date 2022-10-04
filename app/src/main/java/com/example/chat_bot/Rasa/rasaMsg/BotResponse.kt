package com.example.chat_bot.Rasa.rasaMsg

import retrofit2.Call
import retrofit2.http.Body


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



//class BotResponse(var recipient: String = "1234", var text: String = "", var image: String = "", var buttons: List<Buttons>) {
//    inner class Buttons(var payload: String, var title: String = "Button")
//}
