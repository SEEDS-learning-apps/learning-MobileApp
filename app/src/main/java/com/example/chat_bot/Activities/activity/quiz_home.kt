package com.example.chat_bot.Activities.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.databinding.ActivityQuizHomeBinding


class quiz_home : AppCompatActivity() {
    private lateinit var binding: ActivityQuizHomeBinding
    private val SPLASH_TIME: Long = 2000
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()

        handler.postDelayed({
            load_instructions()
            binding.splashProgress.visibility = View.GONE
        },SPLASH_TIME)

        binding.btnNextQuizHome.setOnClickListener { Load_video() }


    }

    private fun Load_video() {
        startActivity(Intent(this,Video::class.java))
    }

    private fun load_instructions() {


        // WebViewClient allows you to handle
        // onPageFinished and override Url loading.
        binding.webView.webViewClient = WebViewClient()



        // this will load the url of the website
        binding.webView.loadUrl("https://www.wineme.uni-siegen.de/projekte/seeds/")

        // this will enable the javascript settings
        binding.webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        binding.webView.settings.setSupportZoom(true)
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}