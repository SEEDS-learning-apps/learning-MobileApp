package com.example.chat_bot.Rasa.Networkings
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.Message
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface api_Rasa {

//    @POST("webhooks/rest/webhook")
//    suspend fun sendMessage(@Body userMessage: UserMessage?): Response<List<BotResponse>>


     @POST("webhooks/rest/webhook")
     fun sendMessage(@Body userMessage: UserMessage?): Call<ArrayList<BotResponse>>



    companion object{
        var retrofitService: api_Rasa? = null

        fun getInstance() : api_Rasa {
            if (retrofitService == null) {
                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .build()
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://ig1mceza29.execute-api.eu-central-1.amazonaws.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://ig1mceza29.execute-api.eu-central-1.amazonaws.com/").client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()

                retrofitService = retrofit.create(api_Rasa::class.java)
            }
            return retrofitService!!
        }
    }
}