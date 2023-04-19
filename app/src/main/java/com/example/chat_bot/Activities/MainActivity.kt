package com.example.chat_bot.Activities


import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_bot.data.Message
import com.example.chat_bot.data.Topics
import com.example.chat_bot.data.msgAdapter
import com.example.chat_bot.databinding.ActivityMainBinding
import com.example.chat_bot.utils.Bot_replies
import com.example.chat_bot.utils.Constants
import com.example.chat_bot.utils.Constants.LEARN
import com.example.chat_bot.utils.Constants.RCV_ID
import com.example.chat_bot.utils.Time.timeStamp
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), msgAdapter.Callbackinter{

    private lateinit var adapter: msgAdapter
    private val TAG = "MyActivity"
    private lateinit var binding: ActivityMainBinding
    var msgBtn: List<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()




    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        recyclerView()
        clickEvents()
        customMsg("Hello, Seeds Asssitant here!!, How may i help you?")

        Log.v(TAG, "In main")

//        setupNotificationWithTrigger(10000)

    }


    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun customMsg(message: String) {

            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main) {
                    val timeStamp = timeStamp()
                    adapter.insertMessage(Message(message, RCV_ID, timeStamp, false,"", msgBtn,""))
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                }
            }

        }

        private fun recyclerView() {

            adapter = msgAdapter(this, this.applicationContext)
            binding.rvMessages.adapter = adapter
            binding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)
            Log.v(TAG, "ReCYCLE")
        }

        private fun clickEvents() {
            //Send a message
            binding.btnSend.setOnClickListener {
                sendMessage()
            }
            binding.etMessage.setOnClickListener {
                GlobalScope.launch {
                    delay(100)
                    withContext(Dispatchers.Main) {
                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                    }
                }
            }
        }

        private fun sendMessage() {
            val message = binding.etMessage.text.toString()
            val timeStamp = timeStamp()

            if (message.isNotEmpty()) {

                binding.etMessage.setText("")
                //Adds it to our local list
                adapter.insertMessage(Message(message, Constants.SND_ID, timeStamp,false,"",msgBtn,""))
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                botResponse(message)
            }
        }

        private fun botResponse(message: String) {
            val timeStamp = timeStamp()
            GlobalScope.launch {
                //Fake response delay
                delay(3000)

                withContext(Dispatchers.Main) {
                    //Gets the response
                    val response = Bot_replies.basicResponses(message,false)

//                //Adds it to our local list
//                messagesList.add(Message(response, RECEIVE_ID, timeStamp))

                    //Inserts our message into the adapter
                    adapter.insertMessage(Message(response as String, RCV_ID, timeStamp, false,"", msgBtn,""))

                    //Scrolls us to the position of the latest message
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                    when (response) {
                        LEARN -> {

                            val intent = Intent(this@MainActivity, QuizActivity::class.java).apply {
                                putExtra(EXTRA_MESSAGE, message)
                            }
                            startActivity(intent)
                        }

                    }

                }
            }
        }

    override fun passResultCallback(message: Topics) {
        TODO("Not yet implemented")
    }
//    private fun setupNotificationWithTrigger(triggerTime: Long) {
//        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val notificationIntent = Intent(this, NotificationReceiver::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(
//            this,
//            0,
//            notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )
//
//        // Set the alarm to go off at the specified time
//        alarmManager.set(
//            AlarmManager.RTC_WAKEUP,
//            System.currentTimeMillis() + triggerTime,
//            pendingIntent
//        )
//    }
}


