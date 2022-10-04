package com.example.chat_bot.networking.Retrofit.Seeds_api.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SEEDSViewModelFact constructor(private val repository: SEEDSRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SEEDSViewModel::class.java)) {
            SEEDSViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
