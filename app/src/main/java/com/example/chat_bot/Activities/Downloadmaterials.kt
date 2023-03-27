package com.example.chat_bot.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R

class Downloadmaterials : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_quiz)

        val backbtn = findViewById<ImageView>(R.id.Backbutton_downloadmaterials)

        backbtn.setOnClickListener{
            val intent = Intent (this.applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

    }
}