package com.example.chat_bot.utils

import com.example.chat_bot.R
import com.example.chat_bot.utils.Constants.LEARN
import com.example.chat_bot.utils.Constants.SEEDS
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat


object Bot_replies {

    fun basicResponses(_message: String, yo:Boolean): Any {


        val random = (0..3).random()
        val message = _message.lowercase()
        val sub:String

        fun greet(): List<String> {
            return listOf(R.array.Languages.toString())
        }
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

//            //subject
//            message.contains("biology") -> {
//
//                when (random) {
//                    0 -> "which topic is intresting for you"
//                    1 -> "which topic you want to learn"
//                    else -> "try saying something else" }
//            }

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

            //Seeds
            message.contains("What is SEEDS project?") || message.contains("Tell me about SEEDS")-> { SEEDS }

            //Learning phase
            message.contains("help") || message.contains("need help") || message.contains("how to learn")
                    || message.contains("Can you help me") || message.contains("Do you need to know: how to use the app")
                    || message.contains("How to ask for an exercise?")
                    || message.contains("use the app")
                    || message.contains("get an exercise")
                    || message.contains("ask for an exercise?")
                    || message.contains("know how to ask for an exercise")-> {

                when (random) {

                    0 -> "Learning is always fun!! So, when you need to learn just say that you want to learn, i will get subjects that you might be intrested in. "
                    1 -> "Sure!! Just say, you want to learn, I will get subjects that you might be interested in. "
                    2 -> "Sure!! I'm happy that you choose SEEDS. Just say, you want to learn, I will get subjects, you might be interested in. "
                    3 -> "I got you!!, Just say, you want to learn, I will get subjects, you might be interested in. "
                    else -> "try saying something else" } }


            //Learning phase
            message.contains("please_publish_sugesstion")  -> {
                when (random) {

            0 -> "i have following choices for you"
            1 -> "ready to learn these??"
            2 -> "Hope you like to study"
            3 -> "I got you!!"
            else -> "try saying something else" } }


            //Learning phase
            message.contains("want to study") || message.contains("want to study") || message.contains("want to learn")

                    || message.contains("study")
                    || message.contains("möchte lernen")
                    || message.contains("lernen wollen")
                    || message.contains("kennenlernen möchten")  -> {
                val id = 12
                when (random) {

                    0 -> "which subject you want to learn" +""+id
                    1 -> "Write me a message with subject name"+""+id
                    2 -> "Send a message with subject name"+""+id
                    3 -> "Sure!!, Can you write me a message with subject name"+""+id
                    else -> "try saying something else" } }

//            message.contains("subject_ka_naam") ->
//            {
//                LEARN
//            }

            message.contains("seeds") ->
            {
                SEEDS
            }



            message.contains("learn") ->
            {
                LEARN
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "try saying something else"
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
                    2 -> "i'm having problems at the moment"
                    else -> "Sorry, i didn't got you"
                }
            }
        }

    }


//    fun launchMcqs(){
//        val intent = Intent(context, Mcqs::class.java)
//       // intent.putExtra("student_id", student)
//        context.startActivity(intent)}
interface Callbackchat {
    fun passResultCallback(message: String)
}
}


