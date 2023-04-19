package com.example.chat_bot.alarm_service

//class Alarmservice : IntentService(Alarmservice::class.java.simpleName) {
//   private val notificationId = System.currentTimeMillis().toInt()
//    override fun onHandleIntent(intent: Intent?) {
//       val action=intent!!.action
//
//
//    }
//
//
//
//        //remind the user in 2 minutes
//        val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        val intent = Intent(this, AlarmReceiver::class.java)
//        val pendingIntent =
//            PendingIntent.getBroadcast(this, notificationId, intent, 0)
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP,Calendar.getInstance().timeInMillis +5*6000,pendingIntent)
//    }
//}