package com.example.chat_bot.Activities.acivity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.utils.SessionManager
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Settings : AppCompatActivity() {

    lateinit var session: SessionManager
    lateinit var userename: String
    private var pref_material_language: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val backbtn = findViewById<ImageView>(R.id.Backbutton_settings)

        backbtn.setOnClickListener{
            onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        val interfacelanguage = findViewById<View>(R.id.interface_language)

            interfacelanguage.setOnClickListener{
                alertbox_language()
        }

        val materialLang = findViewById<View>(R.id.Material_language)

        materialLang.setOnClickListener{
            alertbox_materiallanguage()
        }

        val notification = findViewById<View>(R.id.notification)

        notification.setOnClickListener {
            val intent = Intent (this, NotificationManager::class.java)
            startActivity(intent)
        }

        session = SessionManager(this)
        var user = session.getUserDetails()
        userename = user.get("name").toString()

    }

    private fun alertbox_language() {
        val builder = AlertDialog.Builder(this, R.style.PauseDialog)
            .create()

        val view = LayoutInflater.from(this).inflate(R.layout.language_dialog, null)

        builder.setView(view)
        val btnGerman = view.findViewById<Button>(R.id.btn_german)

        btnGerman.text = "Deutsch"

        btnGerman.setOnClickListener {
            Lingver.getInstance().setLocale(this, "de")
            recreate()
            builder.dismiss()
        }

        val btnSpanish = view.findViewById<Button>(R.id.btn_spanish)
        btnSpanish.text = "Español"

        btnSpanish.setOnClickListener {
            Lingver.getInstance().setLocale(this, "es")
            recreate()
            builder.dismiss()
        }

        val btnGreek = view.findViewById<Button>(R.id.btn_greek)
        btnGreek.text = "ελληνικά"

        btnGreek.setOnClickListener {
            Lingver.getInstance().setLocale(this, "el")
            recreate()
            builder.dismiss()
        }

        val btnEnglish = view.findViewById<Button>(R.id.btn_english)

        btnEnglish.setOnClickListener {
            Lingver.getInstance().setLocale(this, "en")
            recreate()
            builder.dismiss()
        }

        val button = view.findViewById<Button>(R.id.code_return_to_chat)

        button.setOnClickListener {
            builder.dismiss()
        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }


    fun alertbox_materiallanguage() {

        val builder = AlertDialog.Builder(this, R.style.PauseDialog).create()

        val view = LayoutInflater.from(this).inflate(R.layout.material_language_dialog, null)

        builder.setView(view)

        val btnGerman = view.findViewById<Button>(R.id.btn_german)

        btnGerman.setOnClickListener {
            session.save_materialLangPref("German")
            pref_material_language = "German"
            UpdateMaterialLang(pref_material_language!!)
            recreate()

            builder.dismiss()
        }

        val btnSpanish = view.findViewById<Button>(R.id.btn_spanish)
        btnSpanish.setOnClickListener {
            session.save_materialLangPref("Spanish")
            pref_material_language = "Spanish"
            UpdateMaterialLang(pref_material_language!!)
            recreate()
            // CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()
        }

        val btnGreek = view.findViewById<Button>(R.id.btn_greek)
        btnGreek.setOnClickListener {
            session.save_materialLangPref("Greek").toString()
            pref_material_language = "Greek"
            UpdateMaterialLang(pref_material_language!!)
            recreate()
            //  CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()
        }

        val btnEnglish = view.findViewById<Button>(R.id.btn_english)
        btnEnglish.setOnClickListener {
            session.save_materialLangPref("English")
            pref_material_language = "English"
            UpdateMaterialLang(pref_material_language!!)
            recreate()
            //  CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()
        }

        val button = view.findViewById<Button>(R.id.code_return_to_chat)

        button.setOnClickListener {
            builder.dismiss()
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    private fun UpdateMaterialLang(language: String) {
        val dao: SeedsDao = SeedsDatabase.getInstance(this@Settings).seedsDao
        lifecycleScope.launch(Dispatchers.IO) {
            dao.updatedMaterialLanguage(language, userename.toString())
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        // perform additional back navigation logic if necessary
    }
}
