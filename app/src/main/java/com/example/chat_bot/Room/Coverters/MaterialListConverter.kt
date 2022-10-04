package com.example.chat_bot.Room.Coverters

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.room.TypeConverter
import com.example.chat_bot.Rasa.rasaMsg.Button
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.data.tryy.AllQuestion
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.utils.SessionManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MaterialListConverter {



    @TypeConverter
    fun fromStringToMaterialList(value: String?): List<AllQuestion> {
        val listType: Type = object : TypeToken<List<AllQuestion?>?>() {}.type
        Log.d("materialConverter", "from string")
        return Gson().fromJson(value, listType)
    }

//    val emptyList = Gson().toJson(ArrayList<QuestItem>())
//    val jsonString = pref.getString(SessionManager.savedQuiz_KEY, emptyList)
//    val gson = Gson()
//    val type = object : TypeToken<ArrayList<QuestItem?>?>() {}.type
//    return gson.fromJson<ArrayList<QuestItem>>(jsonString, type)


    @TypeConverter
    fun fromMaterialListToString(list: List<AllQuestion?>?): String {
        val gson = Gson()
        Log.d("materialConverter", "from material")

        return gson.toJson(list)
    }
}