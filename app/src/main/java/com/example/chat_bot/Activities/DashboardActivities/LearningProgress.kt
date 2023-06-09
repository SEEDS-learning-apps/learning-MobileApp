package com.example.chat_bot.Activities.DashboardActivities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.SeedsDatabase
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearningProgress : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_android_chart)

        val chart: PieChart = findViewById(R.id.chart)

        val database = SeedsDatabase.getInstance(this)
        val seedsDao = database.seedsDao
        val dbHelper = DatabaseHelper(this)

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
                "Art & Design"
            )

            val counts = subjects.map { subject ->
                val count = dbHelper.getCount(subject)
                subject to count
            }

            withContext(Dispatchers.Main) {
                updateChart(chart, counts)
            }
        }
    }

    private fun updateChart(chart: PieChart, counts: List<Pair<String, Int>>) {
        val filteredCounts = counts.filter { it.second >= 1 }

        val entries = filteredCounts.map { (subject, count) ->
            PieEntry(count.toFloat(), subject)
        }

        val dataSet = PieDataSet(entries, "Dynamic Data")
        dataSet.colors = listOf(Color.BLUE, Color.GRAY, Color.RED, Color.GREEN, Color.YELLOW) // Set colors for the chart slices

        val pieData = PieData(dataSet)
        chart.data = pieData

        chart.animateY(1000)
        chart.invalidate()
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 2)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
