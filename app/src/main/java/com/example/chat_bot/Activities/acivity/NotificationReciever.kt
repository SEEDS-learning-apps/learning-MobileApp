package com.example.chat_bot.Activities.acivity

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, HomeActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            i,
            PendingIntent.FLAG_IMMUTABLE // Add FLAG_IMMUTABLE flag
        )

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
        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123, builder.build())
    }
}
