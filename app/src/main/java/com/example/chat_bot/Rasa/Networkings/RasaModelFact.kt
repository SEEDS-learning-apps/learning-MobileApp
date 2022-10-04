package com.example.chat_bot.Rasa.Networkings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class RasaModelFact constructor(private val repository: Repsoitryrasa): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ViewmodelRasa::class.java)) {
            ViewmodelRasa(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
