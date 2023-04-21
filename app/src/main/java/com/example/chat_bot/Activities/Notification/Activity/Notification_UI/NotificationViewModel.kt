package com.example.chat_bot.Activities.Notification.Activity.Notification_UI

import androidx.lifecycle.ViewModel
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications
import com.crushtech.timelyapp.data.repository.NotificationRepository
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

    //this method is not used nor called yet...
    fun deleteAll()= CoroutineScope(Dispatchers.Main).launch {
        repos.deleteAllAlarms()
    }

    fun getAllAlarms()=repos.getAllAlarms()
}