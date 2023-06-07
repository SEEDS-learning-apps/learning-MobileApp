package com.example.chat_bot.ui

import androidx.lifecycle.ViewModel
import com.crushtech.timelyapp.data.repository.AlarmRepository
import com.example.chat_bot.Room.Entities.Alarms
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(private val repos: AlarmRepository) :ViewModel(){

    fun insert(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.insert(alarm)
    }
    fun update(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.update(alarm)
    }
    fun delete(alarm: Alarms)= CoroutineScope(Dispatchers.Main).launch {
        repos.delete(alarm)
    }

}