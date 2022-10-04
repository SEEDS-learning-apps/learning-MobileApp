package com.example.chat_bot.utils

import android.app.Application
import com.yariksoffice.lingver.Lingver

class Language : Application() {

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this)
    }
}