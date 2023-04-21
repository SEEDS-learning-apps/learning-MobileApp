package com.example.chat_bot.Activities.DashboardActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.chat_bot.R

class Help : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)


        val Contactus = findViewById<View>(R.id.open_Contactus_dialog)

        Contactus.setOnClickListener{
            openContactDialog()
        }

        val backbtn = findViewById<ImageView>(R.id.Backbutton_help)

        backbtn.setOnClickListener{
           onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        val feedback = findViewById<View>(R.id.open_feedback_dialog)

        feedback.setOnClickListener{
            openRatingDialog()
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

    private fun openRatingDialog() {
        val builder = AlertDialog.Builder(this, R.style.swipeDialog).create()

        val view = LayoutInflater.from(this).inflate(R.layout.app_rating, null)
        builder.setView(view)

        val ratingBar = view.findViewById<RatingBar>(R.id.ratingbar)
        val btnConfirm = view.findViewById<Button>(R.id.rate_confirmbtn)
        val ratelaterbtn = view.findViewById<Button>(R.id.rating_laterbtn)
        val textInterest = view.findViewById<TextView>(R.id.interest_text)

        ratingBar.onRatingBarChangeListener = object : RatingBar.OnRatingBarChangeListener {
            override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
                if (fromUser) {
                    textInterest.text = when (rating.toInt()) {
                        1 -> "Not interested"
                        2 -> "Somewhat interested"
                        3 -> "Interested"
                        4 -> "Very interested"
                        5 -> "Extremely interested"
                        else -> ""
                    }
                    btnConfirm.isEnabled = true
                    btnConfirm.alpha = 1.0f
                }
            }
        }

        btnConfirm.isEnabled = false
        btnConfirm.alpha = 0.5f
        btnConfirm.setOnClickListener {
            openFeedbackDialog(ratingBar.rating, textInterest.text.toString())
            builder.dismiss()
        }


        ratelaterbtn.setOnClickListener {
            builder.dismiss()
        }

        builder.show()
    }

    private fun openFeedbackDialog(rating: Float, interestText: String) {
        val builder = AlertDialog.Builder(this, R.style.entryDialog)
            .create()

        val view = LayoutInflater.from(this).inflate(R.layout.app_feedback, null)

        builder.setView(view)
        val btnsubmit = view.findViewById<Button>(R.id.feedback_submitbtn)
        val edittext = view.findViewById<TextView>(R.id.Text_feedback)
        val feedbacklater = view.findViewById<Button>(R.id.feedback_laterbtn)

        btnsubmit.isEnabled = false
        btnsubmit.alpha = 0.5f

        edittext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotBlank()) {
                    btnsubmit.isEnabled = true
                    btnsubmit.alpha = 1.0f
                } else {
                    btnsubmit.isEnabled = false
                    btnsubmit.alpha = 0.5f
                }
            }
        })

        btnsubmit.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("seeds.feedback@gmail.com"))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "User Feedback from Seeds App")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Rating: $rating/5 \n\nUser Experience: $interestText  \n\nFeedback Message: ${edittext.text.toString().capitalize()} ")

            try {
                startActivity(Intent.createChooser(emailIntent, "Send feedback using:"))
            } catch (e: Exception) {
                Toast.makeText(this@Help, "No Email app found", Toast.LENGTH_SHORT).show()
            }
        }

        feedbacklater.setOnClickListener {
            builder.dismiss()
        }

        builder.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)

    }
}