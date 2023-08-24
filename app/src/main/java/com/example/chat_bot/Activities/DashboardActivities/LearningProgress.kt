package com.example.chat_bot.Activities.DashboardActivities

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.airbnb.lottie.LottieAnimationView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import kotlinx.coroutines.*

class LearningProgress : AppCompatActivity() {

    private lateinit var chartView: AnyChartView
    private lateinit var animationView1: LottieAnimationView
    private lateinit var animationView2: LottieAnimationView
    private lateinit var backbtn: ImageView
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        setContentView(R.layout.dashboard_android_chart)

        chartView = findViewById(R.id.chart)
        chartView.visibility = View.INVISIBLE

        backbtn = findViewById(R.id.Backbutton_Learningprogress)
        backbtn.visibility = View.INVISIBLE

        textView = findViewById(R.id.learning_progress_text)
        textView.visibility = View.INVISIBLE

        val deletebtn = findViewById<CardView>(R.id.delete_button)
        deletebtn.visibility = View.INVISIBLE

        animationView1 = findViewById(R.id.animation_view1)
        animationView2 = findViewById(R.id.animation_view2)

        val dbHelper = DatabaseHelper(this)

        // Hide the chart initially
        chartView.visibility = View.GONE

        // Start the animations
        animationView1.setAnimation(R.raw.loading)
        animationView2.setAnimation(R.raw.word)

        animationView1.playAnimation()
        animationView2.playAnimation()

        val deleteButton = findViewById<CardView>(R.id.delete_button)

        backbtn.setOnClickListener{
            onBackPressed()
        }

        // Wait for the animations to finish
        animationView2.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                // Launch a coroutine to retrieve the count for all subjects
                GlobalScope.launch(Dispatchers.IO) {
                    val subjects = listOf(
                        "Business",
                        "Chemistry",
                        "Psychology",
                        "Technology",
                        "Language",
                        "Math",
                        "History",
                        "Biology",
                        "Physics",
                        "Humanities",
                        "Art and Design"
                    )

                    val counts = subjects.map { subject ->
                        val count = dbHelper.getCount(subject)
                        subject to count
                    }

                    withContext(Dispatchers.Main) {
                        // Update the chart with the retrieved data
                        updateChart(counts)

                        // Show the chart after the data is retrieved
                        chartView.visibility = View.VISIBLE
                        backbtn.visibility = View.VISIBLE
                        textView.visibility = View.VISIBLE
                        val delayMillis = 500L
                        Handler().postDelayed({
                            deleteButton.visibility = View.VISIBLE
                        }, delayMillis)
                    }

                    deleteButton.setOnClickListener {
                        onDeleteButtonClick()
                    }
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        // Start the animations after setting up the listener
        val animationSet = AnimatorSet()
        animationSet.playTogether(
            ObjectAnimator.ofFloat(animationView1, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(animationView2, "alpha", 0f, 1f)
        )
        animationSet.duration = 2500
        animationSet.start()
    }

    private fun onDeleteButtonClick() {
        val context = this@LearningProgress

        // Show a confirmation dialog before deleting
        AlertDialog.Builder(context)
            .setTitle("Delete Entries")
            .setMessage("Are you sure you want to delete all entries?")
            .setPositiveButton("Delete") { _, _ ->
                // Launch a coroutine to delete all entries
                GlobalScope.launch(Dispatchers.IO) {
                    val dbHelper = DatabaseHelper(context)
                    val subjects = listOf(
                        "Business",
                        "Chemistry",
                        "Psychology",
                        "Technology",
                        "Language",
                        "Math",
                        "History",
                        "Biology",
                        "Physics",
                        "Humanities",
                        "Art and Design"
                    )

                    // Delete all entries for each subject
                    subjects.forEach { subject ->
                        dbHelper.deleteAllEntries(subject)
                    }

                    withContext(Dispatchers.Main) {
                        // Start the EmptyActivity
                        val intent = Intent(context, EmptyLearningProgress::class.java)
                        startActivity(intent)
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }



    private fun applyTheme() {
        val sharedPrefs: SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("DARK MODE", false)
        val themeResId = if (isDarkMode) R.style.DarkMode else R.style.WhiteMode
        setTheme(themeResId)
    }

    private fun updateChart(counts: List<Pair<String, Int>>) {
        val filteredCounts = counts.filter { it.second >= 1 }

        if (filteredCounts.isNotEmpty()) {
            val totalCompleted = filteredCounts.sumBy { it.second }

            val dataEntries = filteredCounts.map { (subject, count) ->
                val percentage = (count.toDouble() / totalCompleted) * 100
                ValueDataEntry(subject, count).apply {
                    setValue("percentage", percentage)
                }
            }


            val pie3d = AnyChart.pie3d()


            pie3d.data(dataEntries)
            pie3d.tooltip()
                .useHtml(true)
                .format("Topics Completed: {%value} <br/>Percentage: {%percentage}%")


            pie3d.labels().position("outside")
            pie3d.labels().format("{%value}")
            pie3d.labels().fontSize(14)
            pie3d.labels().fontWeight("bold")



            val chartBackground = pie3d.background()
            val sharedPrefs: SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val isDarkMode = sharedPrefs.getBoolean("DARK MODE", false)
            if (isDarkMode) {
                chartBackground.fill(arrayOf("#373737", "#373737", "#283248"))
                pie3d.title().fontColor("#FFFFFF")
                pie3d.legend().title().fontColor("#FFFFFF")
                pie3d.legend().fontColor("#FFFFFF")
                pie3d.labels().fontColor("#FFFFFF")
            } else {
                chartBackground.fill(arrayOf("#EEF5F6", "#C8D5D7", "#67A8D5"))
                pie3d.title().fontColor("#000000")
                pie3d.legend().title().fontColor("#000000")
                pie3d.legend().fontColor("#000000")
                pie3d.labels().fontColor("#000000")
            }

            val legend = pie3d.legend()
            legend.position("centre")
            legend.itemsLayout(LegendLayout.HORIZONTAL)
            legend.align(Align.CENTER)
            legend.fontSize(12)
            legend.margin(45, 0, 0, 0) // Add 50dp top margin, no margin on other sides

            chartView.setChart(pie3d)
            chartView.visibility = View.VISIBLE

        } else {
            chartView.setChart(null)
            chartView.visibility = View.GONE
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
