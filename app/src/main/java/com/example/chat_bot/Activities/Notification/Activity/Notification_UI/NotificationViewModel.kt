package com.example.chat_bot.Activities.Notification.Activity.Notification_UI

import androidx.lifecycle.ViewModel
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.repository.NotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(private val repos: NotificationRepository) :ViewModel(){

    fun insert(alarm: Notifications)= CoroutineScope(Dispatchers.Main).launch {
        repos.insert(alarm)
    }
    fun update(alarm: Notifications)= CoroutineScope(Dispatchers.Main).launch {
        repos.update(alarm)
    }
    fun delete(alarm: Notifications)= CoroutineScope(Dispatchers.Main).launch {
        repos.delete(alarm)
    }

    fun getAllAlarms()=repos.getAllAlarms()
}