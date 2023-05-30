package com.example.chat_bot.Activities.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityPicMatachingBinding

class Pic_mataching : AppCompatActivity() {

    private lateinit var binding: ActivityPicMatachingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPicMatachingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()

        init_spinners()
    }

    private fun init_spinners() {
        val answers = arrayOf(
            "1", "2", "3", "4"
        )
        // access the spinner
        val spinner1 = binding.st1Sp
        if (spinner1 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner1.setAdapter(adapter)

        }

        // access the spinner
        val spinner2 = binding.st2Sp
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner2.setAdapter(adapter)

        }


        // access the spinner
        val spinner3 = binding.st3Sp
        if (spinner3 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner3.setAdapter(adapter)

        }

        // access the spinner
        val spinner4 = binding.st4Sp
        if (spinner4 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner4.setAdapter(adapter)

        }


    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}