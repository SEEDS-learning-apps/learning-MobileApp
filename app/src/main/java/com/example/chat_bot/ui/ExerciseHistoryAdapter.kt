package com.example.chat_bot.ui
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.HomePage.ExerciseFragment
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.ExerciseItemBinding



class ExerciseHistoryAdapter(val context: ExerciseFragment):  RecyclerView.Adapter<ExerciseHistoryAdapter.exViewholder>(){

    var exerciseList: ArrayList<Exercise> = ArrayList()


    inner class exViewholder(val binding: ExerciseItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener{
                exerciseList.removeAt(absoluteAdapterPosition)
                notifyItemRemoved(absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHistoryAdapter.exViewholder {
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
    }
}