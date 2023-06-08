package com.example.chat_bot.Room.Coverters

import androidx.room.TypeConverter
import com.example.chat_bot.Rasa.rasaMsg.Button
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ButtonConverter {
    @TypeConverter
    fun fromStringToButtonsList(value: String?): List<Button> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromButtonListToString(list: List<Button?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}