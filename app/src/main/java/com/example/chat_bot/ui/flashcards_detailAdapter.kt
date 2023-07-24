import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.R
import com.example.chat_bot.data.Exercise

class CustomCardAdapter(private var exerciseList: List<Exercise>) : RecyclerView.Adapter<CustomCardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flashcards_detailitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.questionTextView.text = exercise.subjectName
        holder.answerTextView.text = exercise.answer
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    // Add this method to update the data in the adapter
    fun setData(exerciseList: List<Exercise>) {
        this.exerciseList = exerciseList
        notifyDataSetChanged()
    }
}
