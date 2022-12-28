package com.example.chat_bot.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.R

class mainintroscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainscreeenintro)

        val letsgobtn = findViewById<android.widget.Button>(R.id.lets_go_button)

        letsgobtn.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}