package com.example.chat_bot.Activities.DashboardActivities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.liveData
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class LearningProgress : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_android_chart)

        val chart: PieChart = findViewById(R.id.chart)

        val entries = listOf(
            PieEntry(30f, "Label 1"),
            PieEntry(20f, "Label 2"),
            PieEntry(50f, "Label 3"),
            // Add more data points as needed
        )

        val dataSet = PieDataSet(entries, "Dynamic Data")
        dataSet.colors = listOf(Color.BLUE, Color.GREEN, Color.YELLOW) // Set colors for the chart slices

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