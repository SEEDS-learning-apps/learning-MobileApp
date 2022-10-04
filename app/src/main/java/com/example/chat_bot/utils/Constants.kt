package com.example.chat_bot.utils

import android.view.View
import android.widget.Toast
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase


object Constants {

    const val SND_ID = "SND_ID"
    const val RCV_ID = "RCV_ID"
    const val LEARN = "Learning is fun.."
    const val SEEDS = "SEEDS is a project that proposes a combination of social and digital outreach to spread educational content. Needed learning contents will be communicated by means of chatbots, where municipalities and educational institutions can create learning materials in a pre-defined processes, and cultural mediators, social workers, or individual learners themselves can access the learning material that teachers created."
    const val apiUrl = "http://ec2-3-71-216-21.eu-central-1.compute.amazonaws.com:5000/api/"
    const val DEV_ID = "M-f8f2e818-80-"
    var appMode = "vanilla"

    //val dao: SeedsDao = SeedsDatabase.getInstance(this. ).seedsDao


}


