package com.example.chat_bot.Activities.DashboardActivities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.R
import com.example.chat_bot.data.Exercise
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import flashcardDetailAdapter

class FlashCardDetail : AppCompatActivity() {
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: flashcardDetailAdapter
    private var numberOfQuestions: Int = 0
    private var currentPosition: Int = 0
    private var cardStackLayoutManager: CardStackLayoutManager? = null
    private var isMaxLimitReached = false


    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcards_detail_activity)

        cardStackLayoutManager = CardStackLayoutManager(this)

        val previousButton: AppCompatButton = findViewById(R.id.previousbtn)
        val nextButton: AppCompatButton = findViewById(R.id.Nextbtn)


        previousButton.setOnClickListener {
            cardStackView.rewind()
            navigateToPreviousCard()
        }

        nextButton.setOnClickListener {
            navigateToNextCard()
            cardStackView.swipe()
        }

        val exerciseList = intent.getSerializableExtra("EXERCISE_DETAILS") as? ArrayList<Exercise> ?: arrayListOf()
        val selectedSubject = intent.getStringExtra("EXERCISE_SUBJECT") ?: ""
        val selectedTopic = intent.getStringExtra("EXERCISE_TOPIC") ?: ""

        // Filter the exercises based on the selected subject and topic
        val filteredExercises = exerciseList.filter { exercise ->
            exercise.subjectName == selectedSubject && exercise.topicName == selectedTopic
        }

        if (filteredExercises.isNotEmpty()) {
            numberOfQuestions = filteredExercises[0].questions.size

            // Initialize the adapter before using it
            adapter = flashcardDetailAdapter(filteredExercises[0])

            cardStackView = findViewById(R.id.cardStackView)
            val layoutManager = CardStackLayoutManager(this)
            cardStackView.layoutManager = layoutManager
            cardStackView.adapter = adapter
        } else {

        }
    }

    private fun navigateToNextCard() {
        if (!isMaxLimitReached && currentPosition < numberOfQuestions ) {
            currentPosition++
            // Check if the maximum limit is reached after the swipe
            if (currentPosition >= numberOfQuestions ) {
                // Maximum limit is reached, change the button text to "Close"
                val nextButton: AppCompatButton = findViewById(R.id.Nextbtn)
                nextButton.text = "Close"
                isMaxLimitReached = true
            }
        } else {
            onBackPressed()
        }
    }

    private fun navigateToPreviousCard() {
        if (isMaxLimitReached && currentPosition > 0) {
            while (currentPosition > 0) {
                currentPosition--
            }
            val nextButton: AppCompatButton = findViewById(R.id.Nextbtn)
            nextButton.text = "Next"
            isMaxLimitReached = false
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, FlashCards::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
