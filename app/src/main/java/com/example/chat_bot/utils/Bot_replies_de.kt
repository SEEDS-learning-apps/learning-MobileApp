package com.example.chat_bot.utils

import com.example.chat_bot.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Bot_replies_de {

    fun basicResponses(_message: String, yo:Boolean): Any {


        val random = (0..3).random()
        val message = _message.lowercase()
        val sub:String

        fun greet(): List<String> {
            return listOf(R.array.Languages.toString())
        }
        return when{

            //Hello
            message.contains("hallo") || message.contains("hi")-> {

                when (random) {
                    0 -> "Ahh, Hallo!"
                    1 -> "Wie geht es dir heute?"
                    2 -> "Hallo. Wie läuft dein Tag heute?"
                    3 -> "Hi"
                    4 -> "Hallo, hier ist der SEEDS Assistent! Wie kann ich Dir heute helfen?"
                    else -> "Leider habe ich es nicht verstanden." }
            }

            message.contains("gut") ->
            {
                when (random)
                {
                    0-> "Sehr gut"
                    1-> "Ich fühle mich großartig"

                    else -> "danke"
                }

            }

            //Hello
            message.contains("greet_user") -> {
                when (random) {
                    0 -> "Ahh, Hallo 😁"
                    1 ->   "Hi  😁"
                    2 ->  "Schön dich wieder zu treffen."
                    3 ->   "Wie geht es dir heute?"
                    4 ->   "Hi, wie fühlst du dich heute?"
                    5 ->   "Schön dich wieder zu treffen. Wie fühlst du dich heute?"
                    6 ->   "Hallo. Wie läuft dein Tag heute?"
                    7 ->   "Frohes Lernen"
                    8 -> "Hallo, hier ist der SEEDS Assistent! Wie kann ich Dir heute helfen?"
                    else -> "error"
                }
            }

            //Learning phase
            message.contains("möchte lernen")
                    || message.contains("lernen wollen")
                    || message.contains("kennenlernen möchten")
                    || message.contains("studiengang")
                    || message.contains("studienfachs")  -> {
                val id = 12
    when (random) {

                    0 -> "Ich kann Dir bei einigen wichtigen Themen helfen, die dich vielleicht interessieren"+""+id
                    2 -> "Bitte wähle ein Thema"+""+id
                    3 -> "Bitte wähle ein Thema, um zu beginnen"+""+id
                    4 -> "Bitte wähle ein Thema, um fortzufahren"+""+id
                    else -> "Etwas anderes versuchen" }
            }

            //Learning phase
            message.contains("please_publish_sugesstion")  -> {
                when (random) {

                    0 -> "Bitte wähle ein Thema, um zu beginnen"
                    1 -> "Bitte wähle ein Thema, um fortzufahren."
                    2 -> "Bitte wähle ein Thema"
                    else -> "versuchen Sie, etwas anderes zu sagen" } }

            message.contains("lernen") ->
            {
                Constants.LEARN
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
                    0 -> "Ich habe nicht verstanden, was du geschrieben hast..."
                    1 -> "Try asking me something different"
                    2 -> "Idk"
                    else -> "Versuch mich etwas anderes zu fragen"
                }
            }
        }

    }
}