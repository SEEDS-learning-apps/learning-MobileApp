package com.example.chat_bot.Rasa.Networkings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class Viewmodelfactory constructor(private val repository: Repsoitryrasa): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewmodelRasa::class.java)) {
           Viewmodelfactory(this.repository) as T
        } else {
            throw IllegalArgumentException("Rasa View Model Not Found")
        }
    }
}