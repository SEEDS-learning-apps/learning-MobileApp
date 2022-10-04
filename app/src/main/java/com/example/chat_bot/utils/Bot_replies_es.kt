package com.example.chat_bot.utils

import com.example.chat_bot.R
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object Bot_replies_es {

    fun basicResponses(_message: String, yo: Boolean, s: String): Any {


        val random = (0..3).random()
        val message = _message.lowercase()
        val sub:String

        fun greet(): List<String> {
            return listOf(R.array.Languages.toString())
        }
        return when{

            //Hello
            message.contains("Hola") || message.contains("hi")-> {

                when (random) {
                    0 -> "Hey, hola de nuevo"
                    1 -> "Encantado de verte de nuevo"
                    2 -> "¿Cómo te va?"
                    3 -> "Bienvenido, ¿cómo estás?"
                    4 ->   "Bienvenido, ¿cómo estás?"
                    5 ->   "Hola, ¿cómo te sientes hoy?"
                    6 ->   "Qué bien volver a verte de nuevo, ¿cómo estás hoy?"
                    else -> "Saludos, ¿Qué tal el día?" }
            }

            message.contains("good") ->
            {
                when (random)
                {
                    0-> "Muy bien, gracias"
                    1-> "Me siento fenomenal"

                    else -> "danke"
                }

            }

            //Hello
            message.contains("greet_user") -> {
                when (random) {
                    0 -> "Hey, hola de nuevo 😁"
                    1 ->   "Hi  😁"
                    2 ->  "Encantado de verte de nuevo"
                    3 ->   "¿Cómo te va?"
                    4 ->   "Bienvenido, ¿cómo estás?"
                    5 ->   "Hola, ¿cómo te sientes hoy?"
                    6 ->   "Qué bien volver a verte de nuevo, ¿cómo estás hoy?"
                    7 ->   "Saludos, ¿Qué tal el día?"
                    8 -> "Hola, aquí el asistente de Seeds, ¿puedo ayudarte en algo?"
                    else -> "Feliz aprendizaje"
                }
            }

            message.contains("quiero aprender")->
            {

            }


            //ask age
            message.contains("age") -> {
                when (random) {
                    0 -> "¿Cuál es tu edad?"
                    1 ->   "¿Cuántos años tienes?"
                    2 ->  "Encantado de verte de nuevo"
                    3 ->   "Me gustaría saber tu edad."
                    4 ->   "Bienvenido, ¿cómo estás?"
                    5 ->   "¿Quieres decirme tu edad?"
                    else -> "Desafortunadamente, no te entiendo"
                }
            }

            //ask Access Code
            message.contains("accesscode") -> {
                when (random) {
                    0 -> "Por favor, dame el código de acceso"
                    else -> "Desafortunadamente, no te entiendo"
                }
            }

            //ask Age

            //ask Access Code
            message.contains("age") -> {
                when (random) {
                    0 -> "¿Cuál es tu edad?"
                    0 -> "¿Cuántos años tienes?"
                    0 -> "Me gustaría saber tu edad."
                    0 -> "¿Quieres decirme tu edad?"
                    else -> "Desafortunadamente, no te entiendo"
                }
            }


            //Learning phase
            message.contains("querer aprender")
                    || message.contains("quiero conocer")
                    || message.contains("curso")
                    || message.contains("asunto")  -> {
                val id = 12
                when (random) {


                    0 -> "Puedo ayudarte con algunos temas importantes que te pueden interesar"+""+id
                    1 -> "Por favor elige un tema"+""+id
                    2 -> "Por favor elige un tema para comenzar"+""+id
                    3 -> "Por favor elige un tema para continuar"+""+id
                    else -> "Disculpa las molestias" }
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
                    0 -> "Desafortunadamente, no te entiendo"
                    1 -> "No puedo enternder lo que has enviado"
                    2 -> "No estoy seguro de entender lo que has enviado"
                    else -> "No entiendo qué es lo que quieres"
                }
            }
        }

    }
}