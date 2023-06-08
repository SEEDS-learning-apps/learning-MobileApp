package com.example.chat_bot.Room.Coverters

import android.util.Log
import androidx.room.TypeConverter
import com.example.chat_bot.data.AllQuestion
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

    @TypeConverter
    fun fromMaterialListToString(list: List<AllQuestion?>?): String {
        val gson = Gson()
        Log.d("materialConverter", "from material")

        return gson.toJson(list)
    }
}