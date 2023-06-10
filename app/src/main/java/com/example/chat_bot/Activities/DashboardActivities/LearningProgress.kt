package com.example.chat_bot.Activities.DashboardActivities

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearningProgress : AppCompatActivity() {

    private lateinit var chartView: AnyChartView
    private lateinit var animationView1: LottieAnimationView
    private lateinit var animationView2: LottieAnimationView

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
        setContentView(R.layout.dashboard_android_chart)

        chartView = findViewById(R.id.chart)
        chartView.visibility = View.INVISIBLE

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


    private fun updateChart(counts: List<Pair<String, Int>>) {
        val filteredCounts = counts.filter { it.second >= 1 }

        val dataEntries = filteredCounts.map { (subject, count) ->
            ValueDataEntry(subject, count.toDouble())
        }

        val pie3d = AnyChart.pie3d()
        pie3d.data(dataEntries)

        pie3d.labels().position("outside")
        pie3d.labels().format("{%value}")
        pie3d.labels().fontSize(14) // Set the font size for the labels
        pie3d.labels().fontWeight("bold") // Make the labels bold
        pie3d.labels().fontColor("#000000") // Set the font color for the labels

        val pieLabels = pie3d.labels()
        pieLabels.position("outside")
        pieLabels.format("{%Value}")
        pieLabels.fontSize(14) // Set the font size for the labels
        pieLabels.fontWeight("bold") // Make the labels bold
        pieLabels.fontColor("#000000") // Set the font color for the labels

        val chartBackground = pie3d.background()

        // Check the app theme and set the background accordingly
        val sharedPrefs: SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("DARK MODE", false)
        if (isDarkMode) {
            chartBackground.fill(arrayOf("#373737", "#373737", "#283248")) // Set the gradient colors for dark mode
            pie3d.title().fontColor("#FFFFFF") // Set the title font color to white in dark mode
            pie3d.legend().title().fontColor("#FFFFFF") // Set the legend title font color to white in dark mode
            pie3d.legend().fontColor("#FFFFFF")
        } else {
            chartBackground.fill(arrayOf("#EEF5F6", "#C8D5D7", "#67A8D5")) // Set the gradient colors for light mode
            pie3d.title().fontColor("#000000") // Set the title font color to black in light mode
            pie3d.legend().title().fontColor("#000000") // Set the legend title font color to black in light mode
            pie3d.legend().fontColor("#000000")
        }

        val legend = pie3d.legend()
        legend.position("centre-bottom")
        legend.itemsLayout(LegendLayout.HORIZONTAL)
        legend.align(Align.CENTER)
        legend.fontSize(10)
        legend.margin(0, 0, 0, 10)

        val legendTitle = legend.title()
        legendTitle.enabled(true)
        legendTitle.text("Learning Statistics")
        legendTitle.align(Align.TOP)
        legendTitle.fontSize(25)
        legendTitle.fontWeight("bold") // Make the legend title bold

        chartView.setChart(pie3d)

        pie3d.animation(true)
        pie3d.animation().duration(1000)
    }




    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 2)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
