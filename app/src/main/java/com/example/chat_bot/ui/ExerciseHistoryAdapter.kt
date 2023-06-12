package com.example.chat_bot.ui


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.HomePage.ExerciseFragment
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.ExerciseItemBinding
import com.example.chat_bot.utils.SessionManager


class ExerciseHistoryAdapter(val context: ExerciseFragment):  RecyclerView.Adapter<ExerciseHistoryAdapter.exViewholder>(){

    var exerciseList: ArrayList<Exercise> = ArrayList()
    lateinit var session: SessionManager

    inner class exViewholder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHistoryAdapter.exViewholder {
        session = SessionManager(parent.context)
        return exViewholder(
            ExerciseItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }


    override fun onBindViewHolder(holder: ExerciseHistoryAdapter.exViewholder, position: Int) {
        val ex = exerciseList[position]
        holder.binding.topic1.text = ex.topicName
        holder.binding.attemptTime.text = ex.time
        var final_score= "You got "+ " "+ ex.obtainedscore + " "+ "out of"+ " " + ex.totalscore
        holder.binding.score1.text = final_score
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    fun setExList(exerciseList: ArrayList<Exercise>) {

        this.exerciseList = exerciseList

        Log.d("loggg", exerciseList.toString())
        notifyDataSetChanged()
        checkEmptyState()

    }

    private fun checkEmptyState() {
        val isEmpty = exerciseList.isEmpty()
        context.manageViews(!isEmpty)
    }

    interface ExerciseDeleteListener {
        fun onExerciseDeleted()
    }

}