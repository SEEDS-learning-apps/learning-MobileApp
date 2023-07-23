package com.example.chat_bot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.DashboardActivities.FlashCards
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.FlashcardsItemBinding
import com.example.chat_bot.utils.SessionManager

class LearningProgressAdapter(val context: FlashCards):  RecyclerView.Adapter<LearningProgressAdapter.exViewholder>(){

    var exerciseList: ArrayList<Exercise> = ArrayList()
    lateinit var session: SessionManager

    inner class exViewholder(val binding: FlashcardsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.deleteButton.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    exerciseList.removeAt(position)
                    notifyItemRemoved(position)
                    session.removeListInPref(exerciseList)
                    context.onExerciseDeleted()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LearningProgressAdapter.exViewholder {
        session = SessionManager(parent.context)
        return exViewholder(
            FlashcardsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }


    override fun onBindViewHolder(holder: LearningProgressAdapter.exViewholder, position: Int) {
        val ex = exerciseList[position]
        holder.binding.flashCardsSubjectName.text = ex.subjectName
        holder.binding.flashCardsTopicName.text = "Topic: " + ex.topicName
        holder.binding.attemptTime.text = ex.time
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setExList(exerciseList: ArrayList<Exercise>) {

        val filteredList = ArrayList<Exercise>()
        val subjectsAndTopicsSet = mutableSetOf<Pair<String, String>>()

        for (exercise in exerciseList) {
            val subjectAndTopic = Pair(exercise.subjectName, exercise.topicName)
            if (!subjectsAndTopicsSet.contains(subjectAndTopic)) {
                filteredList.add(exercise)
                subjectsAndTopicsSet.add(subjectAndTopic)
            }
        }

        this.exerciseList.clear()
        this.exerciseList.addAll(filteredList)

        notifyDataSetChanged()
    }

    interface ExerciseDeleteListener {
        fun onExerciseDeleted()
    }

}