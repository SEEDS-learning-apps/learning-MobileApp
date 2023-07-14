package com.example.chat_bot.Activities.Chatbot

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.chat_bot.Activities.HomePage.ChatFragment
import com.example.chat_bot.Rasa.rasaMsg.Button
import com.example.chat_bot.data.Message
import com.example.chat_bot.data.msgAdapter
import com.example.chat_bot.databinding.FragmentChatBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.utils.Bot_replies
import com.example.chat_bot.utils.Bot_replies_de
import com.example.chat_bot.utils.Bot_replies_es
import com.example.chat_bot.utils.Constants
import com.example.chat_bot.utils.DB
import com.example.chat_bot.utils.Time
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BotResponse {
    lateinit var adapter: msgAdapter
    lateinit var binding: FragmentChatBinding
    lateinit var viewModel: SEEDSViewModel
    lateinit var language: String
    var url: String = ""

    var db: DB? = null
    var msgBtn: ArrayList<Button> = arrayListOf()
    var username: String = ""
    var isRasa: Boolean = true
    var isVanilla: Boolean = false
    var islearningstarted: Boolean = false
    val timeStamp = Time.timeStamp()
    var isSubjectfetched: Boolean = false
    var istopicfetched: Boolean = false
    var isMaterialReady: Boolean = false


    fun botResponse(message: String, _yo: Boolean, context: Context) {

        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()

        GlobalScope.launch {
            // Response delay
            delay(2000)
            withContext(Dispatchers.Main) {
                if (isVanilla == false) {
                    if (isRasa) {
                        if (_yo) {
                            val response = Bot_replies.basicResponses(message, false)
                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    ""
                                )
                            )

                            delay(2000)
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    true,
                                    "",
                                    msgBtn,
                                    ""
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        } else {
                            var ans: Boolean
                            // Gets the response
                            val res: Any = Bot_replies.basicResponses(message, false)
                            var response = Bot_replies.basicResponses(message, false)
                            ans = res.toString().contains("12")
                            if (ans) {
                                response = res.toString().replace("12", "")
                                islearningstarted = false
                                process_request(response)
                            }
                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }
                    }
                } else {
                    if (language == "de") {
                        if (_yo) {
                            // Gets the response
                            val response = Bot_replies_de.basicResponses(message, false)
                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            delay(2000)
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    true,
                                    "",
                                    msgBtn,
                                    ""
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        } else {
                            var ans: Boolean
                            // Gets the response
                            val res: Any = Bot_replies_de.basicResponses(message, false)
                            var response = Bot_replies_de.basicResponses(message, false)
                            ans = res.toString().contains("12")

                            if (ans) {
                                response = res.toString().replace("12", "")
                                process_request(response)
                                islearningstarted == false
                            }

                            when (response) {
                                Constants.SEEDS -> {
                                    // Handle SEEDS response
                                }
                            }

                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }
                        return@withContext
                    }

                    if (language == "es") {
                        if (_yo) {
                            // Gets the response
                            val response = Bot_replies_es.basicResponses(message, false, "")
                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            delay(2000)
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    true,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        } else {
                            var ans: Boolean
                            // Gets the response
                            val res: Any = Bot_replies_es.basicResponses(message, false, "")
                            var response = Bot_replies_es.basicResponses(message, false, "")
                            ans = res.toString().contains("12")
                            if (ans) {
                                response = res.toString().replace("12", "")
                                islearningstarted == false
                                process_request(response)
                            }

                            when (response) {
                                Constants.SEEDS -> {
                                    // Handle SEEDS response
                                }
                            }

                            db!!.insertMessage(
                                Message(
                                    response as String,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    ""
                                )
                            )
                            // Inserts our message into the adapter
                            adapter.insertMessage(
                                Message(
                                    response,
                                    Constants.RCV_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn,
                                    username
                                )
                            )

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            // Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }
                        return@withContext
                    }

                    if (_yo) {
                        // Gets the response
                        val response = Bot_replies.basicResponses(message, false)
                        db!!.insertMessage(
                            Message(
                                response as String,
                                Constants.RCV_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                username
                            )
                        )
                        // Inserts our message into the adapter
                        adapter.insertMessage(
                            Message(
                                response,
                                Constants.RCV_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                username
                            )
                        )

                        delay(2000)
                        adapter.insertMessage(
                            Message(
                                response,
                                Constants.RCV_ID,
                                timeStamp,
                                true,
                                "",
                                msgBtn,
                                username
                            )
                        )

                        binding.typingStatus.cancelAnimation()
                        binding.typingStatus.visibility = View.GONE
                        // Scrolls us to the position of the latest message
                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                    } else {
                        var ans: Boolean
                        // Gets the response
                        val res: Any = Bot_replies.basicResponses(message, false)
                        var response = Bot_replies.basicResponses(message, false)
                        ans = res.toString().contains("12")
                        if (ans) {
                            response = res.toString().replace("12", "")
                            islearningstarted = false
                            process_request(response)
                        }

                        db!!.insertMessage(
                            Message(
                                response as String,
                                Constants.RCV_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                username
                            )
                        )
                        // Inserts our message into the adapter
                        adapter.insertMessage(
                            Message(
                                response,
                                Constants.RCV_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn,
                                username
                            )
                        )

                        binding.typingStatus.cancelAnimation()
                        binding.typingStatus.visibility = View.GONE
                        // Scrolls us to the position of the latest message
                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                        when (response) {
                            Constants.SEEDS -> {
                                // Handle SEEDS response
                            }
                        }

                        if (response.contains("want to study") || response.contains("want to learn")
                            || response.contains("study") || response.contains("möchte lernen")
                            || response.contains("lernen wollen")
                            || response.contains("kennenlernen möchten") || response.contains("learn")
                        ) {
                            // Handle study/learning response
                        }
                    }
                }
            }
        }
    }

    private fun process_request(response: String, lifecycleOwner: LifecycleOwner? = null) {
        lifecycleOwner?.let { fetch_subjects(it) }
    }


    private fun fetch_subjects(lifecycleOwner: LifecycleOwner) {
        viewModel.subjectList.observe(lifecycleOwner, Observer {
            Log.d(ContentValues.TAG, "OnCreate: $it")


            if (it.size < 1) {
                ChatFragment().customMsg("Subject List is empty (fetch_subjects)", false, msgBtn)
                !istopicfetched
                !isMaterialReady
                !isSubjectfetched
                return@Observer
            } else
                isSubjectfetched = true
        })

        viewModel.errorMessage.observe(lifecycleOwner, Observer {

            Log.d(ContentValues.TAG, "OnCreate: $it")

            isSubjectfetched = false
            istopicfetched = false

            if (language == "en") {
                ChatFragment().customMsg("I am facing problems at the moment", false, msgBtn)
            }
            if (language == "de") {
                ChatFragment().customMsg("Ich habe derzeit Probleme", false, msgBtn)
            }
            if (language == "es") {
                ChatFragment().customMsg("Actualmente tengo problemas", false, msgBtn)
            }
        })


        viewModel.getAllSubjects()
    }



}
