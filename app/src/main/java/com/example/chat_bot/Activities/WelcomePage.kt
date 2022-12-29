package com.example.chat_bot.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.chat_bot.R

class WelcomePage : AppCompatActivity() {
    private lateinit var alreadyRegisteredUserButton: Button
    private lateinit var getStartedButton: Button
    private lateinit var infoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcomepage)
        supportActionBar?.hide()

        setupViews()
    }

    private fun setupViews() {
        setupAlreadyRegisteredUserButton()
        setupGetStartedButton()
        setupInfoTextViewButton()
    }

    private fun setupAlreadyRegisteredUserButton() {
        alreadyRegisteredUserButton = findViewById(R.id.Already_user_button)

        alreadyRegisteredUserButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
    }

    private fun setupGetStartedButton() {
        getStartedButton = findViewById(R.id.Get_started_button)

        getStartedButton.setOnClickListener {
            startActivity(Intent(this, IntroductionActivity::class.java))
        }
    }

    private fun setupInfoTextViewButton() {
        infoTextView = findViewById(R.id.infoTextView)

        infoTextView.setOnClickListener {
            // TODO: navigate to info view of "What is SEEDS?"
        }
    }
}