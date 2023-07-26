package com.example.chat_bot.Activities.DashboardActivities

import CustomCardAdapter
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.Direction

class FlashCardDetail : AppCompatActivity(), CardStackListener {
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: CustomCardAdapter
    private var quizList: List<AllQuestion> = mutableListOf()
    private var customExercises: MutableList<Exercise> = mutableListOf()
    private var currentPosition: Int = 0
    private var cardStackLayoutManager: CardStackLayoutManager? = null

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
            navigateToPreviousCard()
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

        val position: Int = 0
        if (filteredExercises.isNotEmpty()) {

            // Assuming exerciseList contains the list of Exercise items
            // Create an Exercise instance with the data you want for the cards
            val sharedExercise = Exercise(
                exerciseList[position].questionType ,
                exerciseList[position].subjectName,
                exerciseList[position].topicName,
                exerciseList[position].obtainedscore,
                exerciseList[position].totalscore,
                exerciseList[position].time,
                exerciseList[position].question,
                exerciseList[position].answer,
                exerciseList[position].statment1,
                exerciseList[position].answer1,
                exerciseList[position].statment2,
                exerciseList[position].answer2,
                exerciseList[position].statment3,
                exerciseList[position].answer3,
                exerciseList[position].statment4,
                exerciseList[position].answer4
            )

            // Create six cards with the same data by adding the sharedExercise multiple times
            val customExercises = MutableList(6) { sharedExercise }

            // Initialize the adapter before using it
            adapter = CustomCardAdapter(customExercises)

            cardStackView = findViewById(R.id.cardStackView)
            val layoutManager = CardStackLayoutManager(this)
            cardStackView.layoutManager = layoutManager
            cardStackView.adapter = adapter
        } else {
            // Handle the case where the filteredExercises is empty
            // For example, display an error message or take appropriate action
        }
    }

    private fun navigateToPreviousCard() {

                cardStackView.rewind()

    }

    private fun navigateToNextCard() {
        if (currentPosition < adapter.itemCount - 1) {
            currentPosition++
            cardStackView.swipe()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, FlashCards::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        TODO("Not yet implemented")
    }

    override fun onCardSwiped(direction: Direction?) {
        TODO("Not yet implemented")
    }

    override fun onCardRewound() {
        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        TODO("Not yet implemented")
    }
}

