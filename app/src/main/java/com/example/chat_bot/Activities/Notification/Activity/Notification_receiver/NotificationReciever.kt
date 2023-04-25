package com.example.chat_bot.Activities.Notification.Activity.Notification_receiver

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.Activities.Welcomepage.Login
import com.example.chat_bot.Activities.activity.CreateNotificationActivity.Companion.ALARM_REPEAT_DAYS
import com.example.chat_bot.R
import com.example.chat_bot.utils.SessionManager

class NotificationReciever : BroadcastReceiver() {

    private lateinit var session: SessionManager

    override fun onReceive(context: Context, intent: Intent) {
        session = SessionManager(context)

        // Step 1: Check if the user is logged in
        val isLoggedIn = session.isLoggedIn()

        val selectedDays = intent.getStringArrayExtra(ALARM_REPEAT_DAYS)
        // Step 2: Create an intent to start the appropriate activity
        val targetActivity = if (isLoggedIn) {
            HomeActivity::class.java
        } else {
            Login::class.java
        }
        val i = Intent(context, targetActivity)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        // Step 3: Create a pending intent to start the target activity
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            i,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Step 4: Create and configure the notification builder
        val Seeds_logo = BitmapFactory.decodeResource(
            Resources.getSystem(),
            R.drawable.seeds_logo
        )
        val bigPicStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(Seeds_logo)
            .bigLargeIcon(null)
        val builder = NotificationCompat.Builder(context, "SeedsAndroid")
            .setSmallIcon(R.drawable.seeds_logo)
            .setContentTitle("SEEDS Chat & lEARN")
            .setContentText("Continue your learning today")
            .setAutoCancel(true)
            .setStyle(bigPicStyle)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        // Step 5: Check if notification permission is granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val notificationPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.VIBRATE
            )
            if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
                // Step 6: Request notification permission
                val requestPermissionIntent = Intent(
                    "android.settings.APPLICATION_DETAILS_SETTINGS",
                    android.net.Uri.parse("package:" + context.packageName)
                )
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    requestPermissionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

            }
        }

        // Step 7: Post the notification
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123, builder.build())
    }
}
