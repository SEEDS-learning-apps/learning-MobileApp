package com.example.chat_bot.utils

import android.content.Context
import android.content.SharedPreferences

class AppMode {
    var context: Context
    var mode: String = ""

    constructor(context: Context) {

        this.context = context
    }




    fun setAppMode(mode: String) {
        this. mode = mode
    }

    fun getAppMode(): String {
        return mode
    }
}