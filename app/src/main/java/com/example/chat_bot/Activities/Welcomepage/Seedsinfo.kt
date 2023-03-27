package com.example.chat_bot.Activities.Welcomepage

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.R

class Seedsinfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seedsinfo)

        val backbtn = findViewById<ImageView>(R.id.Backbutton)

        backbtn.setOnClickListener{
            val intent = Intent (this, WelcomePage::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }


    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Seedsinfo, WelcomePage::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }

}