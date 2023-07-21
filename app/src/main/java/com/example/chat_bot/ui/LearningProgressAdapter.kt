package com.example.chat_bot.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.DashboardActivities.LearningCardsActivity
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.LearningcardsItemBinding
import com.example.chat_bot.utils.SessionManager

class LearningProgressAdapter(val context: LearningCardsActivity):  RecyclerView.Adapter<LearningProgressAdapter.exViewholder>(){

    var exerciseList: ArrayList<Exercise> = ArrayList()
    lateinit var session: SessionManager

    inner class exViewholder(val binding: LearningcardsItemBinding) : RecyclerView.ViewHolder(binding.root) {

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
            LearningcardsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }


    override fun onBindViewHolder(holder: LearningProgressAdapter.exViewholder, position: Int) {
        val ex = exerciseList[position]
        holder.binding.flashCardsTopicName.text = ex.topicName
        holder.binding.attemptTime.text = ex.time
//        var final_score= "You got "+ " "+ ex.obtainedscore + " "+ "out of"+ " " + ex.totalscore
//        holder.binding.score1.text = final_score
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setExList(exerciseList: ArrayList<Exercise>) {

        this.exerciseList = exerciseList

        Log.d("loggg", exerciseList.toString())
        notifyDataSetChanged()

    }


    interface ExerciseDeleteListener {
        fun onExerciseDeleted()
    }

}