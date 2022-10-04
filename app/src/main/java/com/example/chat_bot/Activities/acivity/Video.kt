package com.example.chat_bot.Activities.acivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.chat_bot.Activities.Login
import com.example.chat_bot.databinding.ActivityVideoBinding

class Video : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding

    private val SPLASH_TIME: Long = 2000
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.videoView.visibility = View.GONE
        handler.postDelayed({
            load_vid()
            binding.vidProgress.visibility = View.GONE
            binding.videoView.visibility = View.VISIBLE
        },SPLASH_TIME)

        binding.btnNextVideo.setOnClickListener { Load_quiz() }
    }

    private fun Load_quiz() {
        startActivity(Intent(this,MatchingActivity::class.java))
    }

    private fun load_vid() {
        binding.videoView.settings.javaScriptEnabled = true
        binding.videoView.loadUrl("https://www.youtube.com/embed/sg_YIqqprB4")

    }
    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}