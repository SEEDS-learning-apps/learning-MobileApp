package com.example.chat_bot.Activities.Welcomepage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.utils.SessionManager

class Splash : AppCompatActivity() {

    private lateinit var session: SessionManager

    private var splashDuration: Long = 5500
    private val splashHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        session = SessionManager(applicationContext)

        if (session.isLoggedIn()) {
            setContentView(R.layout.activity_splash1)
            splashDuration = 2500
        } else {
            setContentView(R.layout.activity_splash)
        }

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
        overridePendingTransition(R.anim.fade_in, R.anim.zoom_in)
    }

    private fun navigateToWelcomePage() {
        startActivity(Intent(this, WelcomePage::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.zoom_in)
    }

}

