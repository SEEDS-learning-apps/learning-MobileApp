package com.example.chat_bot.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.utils.SessionManager

class Splash : AppCompatActivity() {
    private lateinit var session: SessionManager

    private val splashDuration: Long = 2000
    private val splashHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        session = SessionManager(applicationContext)

        splashHandler.postDelayed({
            handleNavigationByAuthentication()
            finish()
        }, splashDuration)
    }

    private fun handleNavigationByAuthentication() {
        if (session.isLoggedIn()) {
            navigateToHomepage()
        } else {
            navigateToWelcomePage()
        }
    }

    private fun navigateToHomepage() {
        val intent = Intent(this, HomeActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(Uri.parse("success"))
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun navigateToWelcomePage() {
        startActivity(Intent(this, WelcomePage::class.java))
    }
}

