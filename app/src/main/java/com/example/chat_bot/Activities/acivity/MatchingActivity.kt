package com.example.chat_bot.Activities.acivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityMatchingBinding
import com.example.chat_bot.databinding.ActivityVideoBinding

class MatchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        init_spinners()
        load_next()


    }

    private fun load_next() {
        binding.matchingSubmit.setOnClickListener { startActivity(Intent(this,Pic_mataching::class.java)) }
    }

    private fun init_spinners() {
        val answers = arrayOf(
            "a", "b", "c", "d"
        )
        // access the spinner
        val spinner1 = binding.st1Sp
        if (spinner1 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner1.setAdapter(adapter)

//            spinner.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    languages.get(position),
//                    Toast.LENGTH_SHORT).show()
//                lang(languages.get(position))


            //  })
        }

        // access the spinner
        val spinner2 = binding.st2Sp
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner2.setAdapter(adapter)

//            spinner.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    languages.get(position),
//                    Toast.LENGTH_SHORT).show()
//                lang(languages.get(position))


            //  })
        }


        // access the spinner
        val spinner3 = binding.st3Sp
        if (spinner3 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner3.setAdapter(adapter)

//            spinner.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    languages.get(position),
//                    Toast.LENGTH_SHORT).show()
//                lang(languages.get(position))


            //  })
        }

        // access the spinner
        val spinner4 = binding.st4Sp
        if (spinner4 != null) {
            val adapter = ArrayAdapter(this,
                R.layout.ans_dropdwn, answers)
            spinner4.setAdapter(adapter)

//            spinner.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    languages.get(position),
//                    Toast.LENGTH_SHORT).show()
//                lang(languages.get(position))


            //  })
        }


    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}