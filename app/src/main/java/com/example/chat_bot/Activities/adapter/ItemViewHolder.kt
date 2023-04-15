package com.example.chat_bot.Activities

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.R

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var item_parent: CardView = itemView.findViewById(R.id.item_parent)
    var click_parent: RelativeLayout = itemView.findViewById(R.id.click_parent)
    var item_icon: ImageView = itemView.findViewById(R.id.item_icon)
    var item_title: TextView = itemView.findViewById(R.id.item_title)

}
