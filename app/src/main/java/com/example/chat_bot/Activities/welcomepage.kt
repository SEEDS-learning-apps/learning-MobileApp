package com.example.chat_bot.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.Activities.Login
import com.example.chat_bot.R
import com.example.chat_bot.Rasa.rasaMsg.Button
import com.example.chat_bot.utils.SessionManager

class welcomepage : AppCompatActivity() {
    lateinit var session: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcomepage)

        session = SessionManager(applicationContext)
        checkLogin()
        supportActionBar?.hide()

            val AlreadyUserbtn = findViewById<android.widget.Button>(R.id.Already_user_button)

            AlreadyUserbtn.setOnClickListener{
                startActivity(Intent(this, Login::class.java))
            }
        

        val txt = findViewById<TextView>(R.id.textView)
        txt.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
    }
    private fun checkLogin() {
        if(session.isLoggedIn())
        {
            val intent = Intent(this, HomeActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("success"))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

    }
}