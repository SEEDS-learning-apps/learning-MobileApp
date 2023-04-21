package com.example.chat_bot.Activities.Notification.Activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activities.DashboardActivities.Settings
import com.example.chat_bot.Activities.Notification.Activity.Notification_receiver.NotificationReciever
import com.example.chat_bot.R
import com.example.chat_bot.databinding.AlarmManagerBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class NotificationManager : AppCompatActivity() {
    private lateinit var binding: AlarmManagerBinding
    private lateinit var picker: MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AlarmManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        binding.selectTimeBtn.setOnClickListener {
            showTimePicker()
        }


        binding.cancelNotificationBtn.setOnClickListener {
            cancelNotification()
        }

        val backbtn = findViewById<ImageView>(R.id.Backbutton_alarmanager)

        backbtn.setOnClickListener{
            val intent = Intent (this, Settings::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun cancelNotification() {
        val intent = Intent(this, NotificationReciever::class.java)
        pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            FLAG_UPDATE_CURRENT
        )
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Notification Cancelled", Toast.LENGTH_SHORT).show()
    }

    private fun setNotification() {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(this, "Notification Timer set Successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Notification Timer")
            .build()

        picker.addOnPositiveButtonClickListener {
            val hour = picker.hour
            val minute = picker.minute
            val amPm = if (hour < 12) "AM" else "PM"
            var hourFormatted = if (hour == 0 || hour == 12) 12 else hour % 12
            binding.selectedTime.text = String.format("%02d:%02d %s", hourFormatted, minute, amPm)

            calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            setNotification()
        }

        picker.show(supportFragmentManager, "SEEDS")
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
            val intent = Intent(this@NotificationManager, Settings::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }
}