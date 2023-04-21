package com.example.chat_bot.Activities.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.chat_bot.Activities.Notification.Activity.Notification_fragment.NotificationFragment
import com.example.chat_bot.Activities.Welcomepage.WelcomePage
import com.example.chat_bot.R

@SuppressLint("ResourceType")
class Notification_MainActivity : AppCompatActivity() {

    private var fm: androidx.fragment.app.FragmentManager? = null
    private var active: Fragment? = null
    private var fragment1: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notification_activity_main)

        createNotificationChannel()


        fragment1 = NotificationFragment()
        active = fragment1
        fm = supportFragmentManager
        fm!!.beginTransaction().add(R.id.fragmentcontainer, fragment1!!, "1").commit()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "SEEDS CHAT & LEARN"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("SeedsAndroid", name, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Notification_MainActivity, WelcomePage::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }
}
