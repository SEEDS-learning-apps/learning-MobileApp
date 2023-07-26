package com.example.chat_bot.Activities.DashboardActivities

import CustomCardAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class FlashCardDetail : AppCompatActivity() {
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: CustomCardAdapter
    private var quizList: List<AllQuestion> = mutableListOf()
    private var customExercises: MutableList<Exercise> = mutableListOf()
    private var currentPosition: Int = 0
    private var cardStackLayoutManager: CardStackLayoutManager? = null
    private var isMaxLimitReached = false

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
        setContentView(R.layout.flashcards_detail_activity)

        cardStackLayoutManager = CardStackLayoutManager(this)

        val previousButton: AppCompatButton = findViewById(R.id.previousbtn)
        val nextButton: AppCompatButton = findViewById(R.id.Nextbtn)

        previousButton.setOnClickListener {
           cardStackView.rewind()
        }

        nextButton.setOnClickListener {
            navigateToNextCard()
        }

        val exerciseList = intent.getSerializableExtra("EXERCISE_DETAILS") as? ArrayList<Exercise> ?: arrayListOf()
        val selectedSubject = intent.getStringExtra("EXERCISE_SUBJECT") ?: ""
        val selectedTopic = intent.getStringExtra("EXERCISE_TOPIC") ?: ""

        // Filter the exercises based on the selected subject and topic
        val filteredExercises = exerciseList.filter { exercise ->
            exercise.subjectName == selectedSubject && exercise.topicName == selectedTopic
        }

        if (filteredExercises.isNotEmpty()) {
            val exercise = filteredExercises[0]

            // Create an Exercise instance with the data you want for the cards
            val sharedExercise = Exercise(
                exercise.questionType,
                exercise.quizSize,
                exercise.subjectName,
                exercise.topicName,
                exercise.obtainedscore,
                exercise.totalscore,
                exercise.time,
                exercise.question,
                exercise.answer,
                exercise.statment1,
                exercise.answer1,
                exercise.statment2,
                exercise.answer2,
                exercise.statment3,
                exercise.answer3,
                exercise.statment4,
                exercise.answer4
            )

            // Create a list of exercises with 'exercise.quizSize' instances of sharedExercise
            customExercises = MutableList(exercise.quizSize) { sharedExercise }

            // Initialize the adapter before using it
            adapter = CustomCardAdapter(customExercises)

            cardStackView = findViewById(R.id.cardStackView)
            val layoutManager = CardStackLayoutManager(this)
            cardStackView.layoutManager = layoutManager
            cardStackView.adapter = adapter
        } else {

        }
    }

    private fun navigateToNextCard() {
        if (currentPosition < customExercises.size) {
            currentPosition++
            cardStackView.swipe()
            // Check if the maximum limit is reached after the swipe
            if (currentPosition >= customExercises.size) {
                // Maximum limit is reached, change the button text to "Close"
                val nextButton: AppCompatButton = findViewById(R.id.Nextbtn)
                nextButton.text = "Close"
            }
        } else {
            // If the maximum limit is reached, execute the onBackPressed function
            onBackPressed()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, FlashCards::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

}
