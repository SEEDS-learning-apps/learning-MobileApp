package com.example.chat_bot.Activities.acivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityMcqsBinding
import com.example.chat_bot.databinding.ActivityOpenEndedBinding

class OpenEnded : AppCompatActivity() {

    private lateinit var binding: ActivityOpenEndedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_ended)
        setContentView(R.layout.activity_mcqs)
        binding = ActivityOpenEndedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleclicks()

        hideActionBar()
    }

    private fun handleclicks() {
        binding.btnopenEndedsubmit.setOnClickListener { submitAnswer() }
    }

    private fun submitAnswer() {
        binding.openEndendlayout.visibility = View.GONE
        binding.openEndendSubmit.visibility = View.VISIBLE
        binding.returnChat.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}