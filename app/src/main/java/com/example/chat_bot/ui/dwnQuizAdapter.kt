package com.example.chat_bot.ui
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.QuizActivity
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.databinding.DownloadItemBinding
import com.example.chat_bot.utils.Time

class dwnQuizAdapter(val context: Context):  RecyclerView.Adapter<dwnQuizAdapter.dwnViewholder>() {

    var quizList: List<QuestItem> = listOf()
    inner class dwnViewholder(val binding: DownloadItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): dwnQuizAdapter.dwnViewholder {
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



        var topicName: String
        topicName = "your favourite topic is here:"
        val timeStamp = Time.timeStamp()


        holder.binding.TimeStampofDownload.text = activityMaterial.time.toString()
        holder.binding.topic1.text = activityMaterial.topic
        holder.binding.downloadBTN.setOnClickListener {

           // Toast.makeText(this.context, activityMaterial.topic, Toast.LENGTH_SHORT).show()


            doActivities(activityMaterial)


        }
    }

    private fun doActivities(activityMaterial: QuestItem) {
        val intent = Intent(context, QuizActivity::class.java)
          intent.putExtra("OfflineActivity",  "OfflineActivity")
          intent.putExtra("offlineQuiz", activityMaterial)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    fun setdwnList(quizList: List<QuestItem>) {

        this.quizList = quizList

        Log.d("loggg", quizList.toString())
        notifyDataSetChanged()
    }
}