package com.example.chat_bot.Activities.Notification.Activity.Notification_UI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.repository.NotificationRepository


@Suppress("UNCHECKED_CAST")
class NotificationViewModelFactory(private val repository: NotificationRepository):
    ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificationViewModel(repository) as T
    }
}