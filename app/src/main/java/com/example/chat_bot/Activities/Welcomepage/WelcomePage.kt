package com.example.chat_bot.Activities.Welcomepage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.chat_bot.Activities.Introduction.IntroductionActivity
import com.example.chat_bot.Activities.activity.Notification_MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityWelcomepageBinding
import com.example.chat_bot.utils.SessionManager
import com.yariksoffice.lingver.Lingver

class WelcomePage : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomepageBinding
    private lateinit var alreadyRegisteredUserButton: Button
    private lateinit var getStartedButton: Button
    private lateinit var infoTextView: TextView
    lateinit var session: SessionManager
    private lateinit var user_language: String


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)

        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_welcomepage)

//        Animations
        val anim = AnimationUtils.loadAnimation(this,R.anim.open_in)
        val bt1Anim = AnimationUtils.loadAnimation(this,R.anim.bt1)
        val bt2Anim = AnimationUtils.loadAnimation(this,R.anim.bt2)
        val bt3Anim = AnimationUtils.loadAnimation(this,R.anim.bt3)


        val bt1 = findViewById<Button>(R.id.Get_started_button)
        val bt2 = findViewById<Button>(R.id.Already_user_button)
        val bt3 = findViewById<TextView>(R.id.infoTextView)


        val seedsLogo = findViewById<ImageView>(R.id.splash_image)
        val seedsText = findViewById<TextView>(R.id.splash_text)

        seedsLogo.startAnimation(anim)
        seedsText.startAnimation(anim)
        bt1.startAnimation(bt1Anim)
        bt2.startAnimation(bt2Anim)
        bt3.startAnimation(bt3Anim)


        setupViews()
        session = SessionManager(applicationContext)
        user_language = ""



    }



    private fun setlang() {
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the language spinner
        val lang_spinner = binding.langBtnn
        lang_spinner.dismissDropDown()
        if (lang_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.lang_dropdown, languages)
            lang_spinner.setAdapter(adapter)

            lang_spinner.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id ->

                    user_language = languages[position]
                    lang(languages[position], adapter)

                }

        }

    }

    private fun setupViews() {
        setupAlreadyRegisteredUserButton()
        setupGetStartedButton()
        setupInfoTextViewButton()
    }

    private fun setupAlreadyRegisteredUserButton() {
        alreadyRegisteredUserButton = findViewById(R.id.Already_user_button)

        alreadyRegisteredUserButton.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)

        }

    }

    private fun setupGetStartedButton() {
        getStartedButton = findViewById(R.id.Get_started_button)

        getStartedButton.setOnClickListener {
            val intent = Intent(this@WelcomePage, IntroductionActivity::class.java)
            startActivity(intent)
            finish()

            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }

    }

    private fun setupInfoTextViewButton() {
        infoTextView = findViewById(R.id.infoTextView)

        infoTextView.setOnClickListener {
            val intent = Intent(this@WelcomePage, Notification_MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }



    private fun lang(lang: String, adapter: ArrayAdapter<String>) {

        when (lang) {
            "Deutsch" -> {

                Lingver.getInstance().setLocale(this, "de")
                recreate()
                adapter.notifyDataSetChanged()
//                overridePendingTransition(R.anim.fade_in,R.anim.fade_out)


            }
            "Español" -> {

                Lingver.getInstance().setLocale(this, "es")
                recreate()
                adapter.notifyDataSetChanged()

            }
            "English" -> {

                Lingver.getInstance().setLocale(this, "en")
                recreate()
                adapter.notifyDataSetChanged()

            }
            "Ελληνικά" -> {

                Lingver.getInstance().setLocale(this, "el")
                recreate()
                adapter.notifyDataSetChanged()

            }
        }
        adapter.notifyDataSetChanged()

    }

    override fun onResume() {
        super.onResume()
        setlang()



    }



}








