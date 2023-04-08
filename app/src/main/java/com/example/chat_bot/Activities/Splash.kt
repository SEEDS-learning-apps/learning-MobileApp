package com.example.chat_bot.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.Activities.Welcomepage.WelcomePage
import com.example.chat_bot.R
import com.example.chat_bot.utils.SessionManager

class Splash : AppCompatActivity() {

    private lateinit var session: SessionManager

    private var splashDuration: Long = 5500
    private val splashHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
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

