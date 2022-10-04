package com.example.chat_bot.Rasa.Networkings


import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import retrofit2.Call
import retrofit2.Response

class Repsoitryrasa constructor(private val retrofitService: api_Rasa)  {

    suspend fun sendmessage(message: UserMessage) : Call<ArrayList<BotResponse>> {
        return retrofitService.sendMessage(message)
    }
}