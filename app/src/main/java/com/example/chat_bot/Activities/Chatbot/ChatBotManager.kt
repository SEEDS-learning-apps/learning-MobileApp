package com.example.chat_bot.Activities.Chatbot

import android.util.Log
import android.view.View
import com.example.chat_bot.Rasa.Networkings.api_Rasa
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.Button
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.Message
import com.example.chat_bot.data.Topics
import com.example.chat_bot.data.msgAdapter
import com.example.chat_bot.databinding.FragmentChatBinding
import com.example.chat_bot.utils.Constants
import com.example.chat_bot.utils.DB
import com.example.chat_bot.utils.Time
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class ChatBotManager {
    private var url = ""
    private lateinit var binding: FragmentChatBinding
    private var db: DB? = null
    private lateinit var language: String

    var filterd_topics: ArrayList<Topics> = arrayListOf()
    var m_androidId: String? = null
    var msgBtn: ArrayList<Button> = arrayListOf()
    var username: String = ""


    fun sendMessage(
        message: String,
        binding: FragmentChatBinding,
        adapter: msgAdapter,
        display: Boolean = true
    ) {
        this.binding = binding

        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()
        val timeStamp = Time.timeStamp()

        binding.btnSend.isEnabled = true
        binding.etMessage.isEnabled = true

        if (filterd_topics.size > 0) {
            filterd_topics.clear()
        }

        Log.d("user_id", m_androidId.toString())

        val userMessage = UserMessage(m_androidId.toString(), message)

        if (display) {
            db?.insertMessage(
                Message(
                    message,
                    Constants.SND_ID,
                    timeStamp,
                    false,
                    "",
                    msgBtn,
                    username
                )
            )

            adapter.insertMessage(
                Message(
                    message,
                    Constants.SND_ID,
                    timeStamp,
                    false,
                    "",
                    msgBtn,
                    ""
                )
            )

            binding.typingStatus.visibility = View.GONE
            binding.typingStatus.cancelAnimation()

            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
        }

        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                val okHttpClient = OkHttpClient()
                val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val messageSender = retrofit.create(api_Rasa::class.java)
                val response = messageSender.sendMessage(userMessage)

                response.enqueue(object : Callback<ArrayList<BotResponse>> {
                    override fun onResponse(
                        call: Call<ArrayList<BotResponse>>,
                        response: Response<ArrayList<BotResponse>>
                    ) {
                        if (response.body() != null && response.body()!!.size != 0) {
                            for (i in 0 until response.body()!!.size) {
                                val message = response.body()!![i]

                                try {
                                    if (message.text.isNotEmpty()) {
                                        db?.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                false,
                                                "",
                                                msgBtn,
                                                username
                                            )
                                        )

                                        adapter.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                false,
                                                "",
                                                msgBtn,
                                                username
                                            )
                                        )

                                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                                    } else if (message.image.isNotEmpty()) {
                                        // Handle image response
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }

                                try {
                                    if (message.buttons != null) {
                                        this@ChatBotManager.msgBtn = message.buttons as ArrayList<Button>
                                        adapter.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                true,
                                                "",
                                                msgBtn,
                                                username
                                            )
                                        )

                                        binding.btnSend.isEnabled = false
                                        binding.etMessage.isEnabled = false

                                        for (item in message.buttons) {
                                            filterd_topics.distinct()
                                            filterd_topics.addAll(
                                                listOf(
                                                    Topics(
                                                        item.payload,
                                                        item.title,
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        ""
                                                    )
                                                )
                                            )
                                        }

                                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                                        Log.d("btnz", message.buttons.toString())
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        } else {
                            val message = "Sorry, something went wrong:\nReceived empty response."

                            db?.insertMessage(
                                Message(
                                    message,
                                    Constants.SND_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            adapter.insertMessage(
                                Message(
                                    message,
                                    Constants.SND_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }

                        binding.typingStatus.visibility = View.GONE
                        binding.typingStatus.cancelAnimation()
                    }

                    override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
                        t.printStackTrace()
                        val message = "Sorry, something went wrong:\n" + t.message

                        db?.insertMessage(
                            Message(
                                message,
                                Constants.SND_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                username
                            )
                        )

                        adapter.insertMessage(
                            Message(
                                message,
                                Constants.SND_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                ""
                            )
                        )

                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        binding.typingStatus.visibility = View.GONE
                        binding.typingStatus.cancelAnimation()
                    }
                })
            }
        }
    }

    fun checklang() {
        language = Lingver.getInstance().getLanguage()
        if (language == "de") {
            url = "https://l0mgxbahu7.execute-api.eu-central-1.amazonaws.com"
        } else if (language == "es") {
            url = "https://l21uryvtf9.execute-api.eu-central-1.amazonaws.com"
        } else if (language == "el") {
            url = "https://i9490x4qog.execute-api.eu-central-1.amazonaws.com"
        } else url = "https://ig1mceza29.execute-api.eu-central-1.amazonaws.com/"
        Log.d("ChatFragment", "language = $language")
    }

}
