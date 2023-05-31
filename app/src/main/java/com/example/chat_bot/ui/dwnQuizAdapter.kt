package com.example.chat_bot.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.activity.QuizActivity
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.databinding.DownloadItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class dwnQuizAdapter(private val context: Context) : RecyclerView.Adapter<dwnQuizAdapter.dwnViewholder>() {

    var quizList: List<QuestItem> = listOf()
    private var listener: dwnQuizAdapterListener? = null

    inner class dwnViewholder(val binding: DownloadItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    // Perform deletion logic here
                    deleteCard(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dwnQuizAdapter.dwnViewholder {
        return dwnViewholder(
            DownloadItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: dwnQuizAdapter.dwnViewholder, position: Int) {
        val activityMaterial = quizList[position]

        holder.binding.TimeStampofDownload.text = activityMaterial.time.toString()
        holder.binding.topic1.text = activityMaterial.topic
        holder.binding.downloadBTN.setOnClickListener {
            doActivities(activityMaterial)
        }
    }

    private fun doActivities(activityMaterial: QuestItem) {
        val intent = Intent(context, QuizActivity::class.java)
        intent.putExtra("OfflineActivity", "OfflineActivity")
        intent.putExtra("offlineQuiz", activityMaterial)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    fun setdwnList(quizList: List<QuestItem>) {
        this.quizList = quizList
        notifyDataSetChanged()
        checkEmptyState()
    }

    private fun deleteCard(position: Int) {
        if (position in 0 until quizList.size) {
            val deletedItem = quizList[position]
            quizList = quizList.toMutableList().apply { removeAt(position) }
            notifyItemRemoved(position)
            Toast.makeText(context, "Topic '${deletedItem.topic}' deleted", Toast.LENGTH_SHORT).show()

            // Delete the item from the database
            val dao: SeedsDao = SeedsDatabase.getInstance(context).seedsDao
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteMaterial(deletedItem)
            }

            // Check if the list becomes empty after deletion
            checkEmptyState()
        }
    }

    private fun checkEmptyState() {
        if (quizList.isEmpty()) {
            listener?.manageViews(true)
        }
    }

    fun setListener(listener: dwnQuizAdapterListener) {
        this.listener = listener
    }

    interface dwnQuizAdapterListener {
        fun manageViews(isEmpty: Boolean)
    }
}

