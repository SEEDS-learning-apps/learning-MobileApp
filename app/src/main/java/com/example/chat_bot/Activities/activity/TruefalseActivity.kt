package com.example.chat_bot.Activities.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat_bot.R
import com.example.chat_bot.data.Data
import com.example.chat_bot.data.Mcqss
import com.example.chat_bot.data.Topics
import com.example.chat_bot.databinding.ActivityTruefalseBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.ui.Tfadapter
import com.example.chat_bot.utils.SessionManager

class TruefalseActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 2000
    lateinit var session: SessionManager
    val handler = Handler(Looper.getMainLooper())
    lateinit var viewModel: SEEDSViewModel
    private lateinit var binding: ActivityTruefalseBinding
    private var correct_answers: Int = 0
    var filterd_trufalses: ArrayList<Data> = arrayListOf()
    private var totale_mcq : Int = 0
    private val retrofitService = SEEDSApi.getInstance()
    val adapter = Tfadapter(this)


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truefalse)

        binding = ActivityTruefalseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(SEEDSViewModel::class.java)
        session = SessionManager(applicationContext)

            filterd_trufalses = intent.getSerializableExtra("filterd_trufalses") as ArrayList<Data>
            correct_answers = intent.getIntExtra("scores", correct_answers)
            totale_mcq = intent.getIntExtra("total_mcqs", totale_mcq)

        var country: String = ""

        country=  session.pref.getString("country", country).toString()
        Log.d("OnCreate", country)



        binding.tfRv.layoutManager
        binding.tfRv.adapter = adapter
        binding.loadingProgress.visibility = View.VISIBLE

        adapter.setMcqList(filterd_trufalses, correct_answers, totale_mcq)

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, "Error went", Toast.LENGTH_SHORT).show()
            binding.loadingProgress.visibility = View.GONE
        })

        handler.postDelayed({
            viewModel.getAllTF()

            binding.loadingProgress.visibility = View.GONE
        },SPLASH_TIME)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}