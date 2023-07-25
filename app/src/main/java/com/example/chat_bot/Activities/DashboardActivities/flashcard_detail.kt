package com.example.chat_bot.Activities.DashboardActivities

import CustomCardAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView

class FlashCardDetail : AppCompatActivity() {
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: CustomCardAdapter
    private var quizList: List<AllQuestion> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcards_detail_activity)

        val exerciseList = intent.getSerializableExtra("EXERCISE_DETAILS") as? ArrayList<Exercise> ?: arrayListOf()
        val selectedSubject = intent.getStringExtra("EXERCISE_SUBJECT") ?: ""
        val selectedTopic = intent.getStringExtra("EXERCISE_TOPIC") ?: ""

        // Filter the exercises based on the selected subject and topic
        val filteredExercises = exerciseList.filter { exercise ->
            exercise.subjectName == selectedSubject && exercise.topicName == selectedTopic
        }

        if (filteredExercises.isNotEmpty()) {
            // Now you can use the filteredExercises list to display the details
            // For example, you can pass it to the adapter to display the cards
            adapter = CustomCardAdapter(filteredExercises)
            adapter.setQuizData(quizList)

            cardStackView = findViewById(R.id.cardStackView)
            val layoutManager = CardStackLayoutManager(this)
            cardStackView.layoutManager = layoutManager
            cardStackView.adapter = adapter
        } else {
            // Handle the case where the filteredExercises is empty
            // For example, display an error message or take appropriate action
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, FlashCards::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
