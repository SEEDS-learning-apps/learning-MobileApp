package com.example.chat_bot.Rasa.Networkings

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.User
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewmodelRasa constructor(private val repository: Repsoitryrasa)  : ViewModel() {

    val myresponse: MutableLiveData<Call<List<BotResponse>>> = MutableLiveData()
    val msglist = MutableLiveData<List<BotResponse>>()
    val errorMessage = MutableLiveData<String>()


//    fun sendMsg(message: UserMessage)
//    {
//        viewModelScope.launch {
//            val response : Call<ArrayList<BotResponse>> = repository.sendmessage(message)
//            response.enqueue()
//
//
//
//        }
//    }

    fun sendMsg(message: UserMessage) {

        viewModelScope.launch {
            val response = repository.sendmessage(message)

            response.enqueue(object : Callback<ArrayList<BotResponse>>{
                override fun onResponse(
                    call: Call<ArrayList<BotResponse>>,
                    response: Response<ArrayList<BotResponse>>
                ) {
                    if (response.body() == null || response.body()!!.isEmpty()) {
                        val botMessage = "Sorry didn't understand"
                        // showTextView(botMessage,BOT,date.toString())
                    } else {
                        val botResponse = response.body()!![0]

                        msglist.postValue(response.body())
                        //  showTextView(botResponse.text,BOT,date.toString())
                        if (botResponse.buttons != null) {
                            Log.e("Button c", "${botResponse.buttons.size}")
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
                    val botMessage = "Check your network connection"
                    // showTextView(botMessage,BOT,date.toString())
                    t.printStackTrace()
                    errorMessage.postValue(t.message.toString())
                    Log.d("jaka", t.message.toString())

                }
            })

        }
    }
}