package com.example.chat_bot.Activities.DashboardActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.ActivityFlashcardsBinding
import com.example.chat_bot.ui.FlashcardAdapter
import com.example.chat_bot.utils.SessionManager

class FlashCards : AppCompatActivity(), FlashcardAdapter.ExerciseDeleteListener {

    var exerciseList: ArrayList<Exercise> = arrayListOf()
    val adapter = FlashcardAdapter(this)
    private lateinit var binding: ActivityFlashcardsBinding
    lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs: SharedPreferences =
            getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedPrefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            // if true then change the theme to dark mode
            setTheme(R.style.DarkMode)
        } else {
            setTheme(R.style.WhiteMode)
        }

        binding = ActivityFlashcardsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        recyclerView()

        val backbtn = findViewById<ImageView>(R.id.Backbutton_learningCards)

        backbtn.setOnClickListener{
            onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

    }

    private fun recyclerView() {
        val adapter = FlashcardAdapter(this)

        binding.flashcardsRv.adapter = adapter
        binding.flashcardsRv.layoutManager = GridLayoutManager(this,2)

        exerciseList = session.readListFromPref(this) as ArrayList<Exercise>
        updateBackgroundVisibility()
        if (exerciseList.isNotEmpty()) {
            manageViews()
            adapter.setExList(exerciseList)
        } else {
            manageViews(false)
        }
    }

    fun manageViews(hasExercise: Boolean = true) {
        binding.flashcardsRv.visibility = if (hasExercise) View.VISIBLE else View.GONE
        binding.noExercise.visibility = if (hasExercise) View.GONE else View.VISIBLE
    }

    override fun onExerciseDeleted() {
        val isEmpty = exerciseList.isEmpty()
        manageViews(!isEmpty)
        updateBackgroundVisibility()
    }

    private fun updateBackgroundVisibility() {
        if (exerciseList.isEmpty()) {
            binding.backgroundImage.visibility = View.GONE // Hide backgroundImage
            binding.noExercise.visibility = View.VISIBLE // Show "No Exercises" view
            binding.flashcardsRv.visibility = View.GONE // Hide RecyclerView
        } else {
            binding.backgroundImage.visibility = View.VISIBLE // Show backgroundImage
            binding.noExercise.visibility = View.GONE // Hide "No Exercises" view
            binding.flashcardsRv.visibility = View.VISIBLE // Show RecyclerView
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 2)
        startActivity(intent)
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
