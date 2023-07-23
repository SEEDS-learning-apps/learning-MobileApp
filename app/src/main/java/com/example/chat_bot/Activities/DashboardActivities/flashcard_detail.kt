package com.example.chat_bot.Activities.DashboardActivities

import CustomCardAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chat_bot.R
import com.example.chat_bot.data.CardItem
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView

class FlashCardDetail : AppCompatActivity() {
    private lateinit var cardStackView: CardStackView
    private lateinit var adapter: CustomCardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flashcards_detail_activity)

        cardStackView = findViewById(R.id.cardStackView)
        adapter = CustomCardAdapter(createDummyData())

        val layoutManager = CardStackLayoutManager(this)
        cardStackView.layoutManager = layoutManager
        cardStackView.adapter = adapter
    }

    private fun createDummyData(): List<CardItem> {
        val cardItems = mutableListOf<CardItem>()
        cardItems.add(CardItem(R.drawable.seeds_logo, "Card 1"))
        cardItems.add(CardItem(R.drawable.app_background_light, "Card 2"))
        cardItems.add(CardItem(R.drawable.trophy, "Card 3"))
        return cardItems
    }
}