package com.example.chat_bot.ui

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.R
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.tryy.AllQuestion
import com.example.chat_bot.databinding.*
import com.example.chat_bot.utils.SessionManager
import java.lang.Exception

class ChatAdapter(var context: Context, var messageList: ArrayList<UserMessage>): RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {
    private var USER_LAYOUT = 1234
    private var BOT_TXT_LAYOUT = 0
    private var BOT_IMG_LAYOUT = 1
    private var BOT_BUT_LAYOUT = 2

    class MessageViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val messageView  = view.findViewById<TextView>(R.id.message_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        var view: View

        if (viewType == BOT_TXT_LAYOUT || viewType == BOT_IMG_LAYOUT) {
            view = LayoutInflater.from(context).inflate(R.layout.bot_message, parent, false)
        } else if (viewType == BOT_BUT_LAYOUT) {
            //view = LayoutInflater.from(context).inflate(R.layout.bot_message_box, parent, false)
            view = LayoutInflater.from(context).inflate(R.layout.activity_main, parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.user_message, parent, false)
        }

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]

        if (message.sender == BOT_TXT_LAYOUT.toString()){ // || message.sender == BOT_BUT_LAYOUT.toString()) {
            holder.messageView.text = message.message as String


            try {

            } catch (e: Exception) { }

        } else if (message.sender == BOT_IMG_LAYOUT.toString()) {
//            val image = holder.image_view as ImageView
//            Glide.with(context).load(message.message).into(image)

            try {
                holder.messageView.visibility == View.GONE

            } catch (e: Exception) { }

        } else if (message.sender == BOT_BUT_LAYOUT.toString()) {
            try {
                holder.messageView.visibility == View.GONE
                //  holder.image_view.visibility = View.GONE
                //holder.time_view.visibility = View.GONE
            } catch (e: Exception) { }

        } else { // USER_LAYOUT
            holder.messageView.text = message.message as String

            try {
                // holder.image_view.visibility = View.GONE
                //holder.time_view.visibility = View.GONE
            } catch (e: Exception) { }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)

        val view = messageList[position]

        if (view.sender == BOT_TXT_LAYOUT.toString()) {
            return BOT_TXT_LAYOUT
        } else if (view.sender == BOT_IMG_LAYOUT.toString()) {
            return BOT_IMG_LAYOUT
        } else if (view.sender == BOT_BUT_LAYOUT.toString()) {
            return BOT_BUT_LAYOUT
        } else { // USER_LAYOUT
            return USER_LAYOUT
        }
    }
}