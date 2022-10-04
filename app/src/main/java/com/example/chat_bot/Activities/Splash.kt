package com.example.chat_bot.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.R

class Splash : AppCompatActivity() {

    private val SPLASH_TIME: Long = 2000
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed({
            startActivity(Intent(this,Login::class.java))
            finish()
        },SPLASH_TIME)
    }
}

