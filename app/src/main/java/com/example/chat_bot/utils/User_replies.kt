package com.example.chat_bot.utils

import com.example.chat_bot.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object User_replies {
    fun basicResponses(_message: String): Any {


        val random = (0..3).random()
        val message = _message.lowercase()
        val sub:String

        return when{

            //Hello
            message.contains("hello") || message.contains("hi")-> {

                when (random) {
                    0 -> "Hello there!"
                    1 -> "ready to learn??"
                    2 -> "Hope you are doing good"
                    3 -> "Hi"
                    else -> "try saying something else" }
            }

            //Hello
            message.contains("greet_user") -> {
                when (random) {
                    0 -> "Ahh Hi again  😁"
                    1 ->   "Hi  😁"
                    2 ->  "Welcome"
                    3 ->   "Nice to meet you again"
                    4 ->   "Hello"
                    else -> "try saying something else"
                }
            }

            //Learning phase
            message.contains("want to learn") || message.contains("want to study")
                    || message.contains("subject choices") || message.contains("subjects")  -> {


                when (random) {

                    0 -> "i have following choices for you"
                    1 -> "ready to learn these??"
                    2 -> "Hope you like to study"
                    3 -> "I got you!!"
                    else -> "try saying something else" }
            }


            message.contains("learn") ->
            {
                Constants.LEARN
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "error"
                }
            }
        }

    }

    fun greet(): String {
        var greeting = listOf<String>("Example", "Program", "Tutorial")
        return greeting.random()
    }
}