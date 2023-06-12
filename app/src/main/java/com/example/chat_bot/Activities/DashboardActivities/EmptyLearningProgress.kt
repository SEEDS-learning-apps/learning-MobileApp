package com.example.chat_bot.Activities.DashboardActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R

class EmptyLearningProgress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_emptystatistics)

        val backbtn = findViewById<ImageView>(R.id.Backbutton_learningprogress)

        backbtn.setOnClickListener{
            onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 2)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}