package com.example.chat_bot.Activities.acivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.chat_bot.Ac.DashboardFragment
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)


        val Contactus = findViewById<ImageView>(R.id.open_Contactus_dialog)

        Contactus.setOnClickListener{
            openContactDialog()
        }

        val backbtn = findViewById<ImageView>(R.id.Backbutton_help)

        backbtn.setOnClickListener{
            val intent = Intent (this.applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }



    }

    private fun openContactDialog() {
        val builder = AlertDialog.Builder(this, R.style.PauseDialog)
            .create()

        val view = LayoutInflater.from(this).inflate(R.layout.contact_us_dialog, null)

        builder.setView(view)
        val btnclose = view.findViewById<Button>(R.id.close_contact_dialog)

        btnclose.setOnClickListener {

            builder.dismiss()
        }
        builder.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Help, HomeActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }
}