import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise

class CustomCardAdapter(private var exerciseList: List<Exercise>) : RecyclerView.Adapter<CustomCardAdapter.ViewHolder>() {

    var quiz = mutableListOf<AllQuestion>()
    var position: Int? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
        val answer1: TextView = itemView.findViewById(R.id.answer)
        val question2: TextView = itemView.findViewById(R.id.matchquestion2)
        val question3: TextView = itemView.findViewById(R.id.matchquestion3)
        val question4: TextView = itemView.findViewById(R.id.matchquestion4)
        val answer2: TextView = itemView.findViewById(R.id.matchAnswer2)
        val answer3: TextView = itemView.findViewById(R.id.matchAnswer3)
        val answer4: TextView = itemView.findViewById(R.id.matchAnswer4)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flashcards_detailitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.questionTextView.text = exercise.question
        holder.answerTextView.text = exercise.answer

        when (exercise.questionType) {
            "4" -> {
                holder.question2.visibility = View.VISIBLE
                holder.answer2.visibility = View.VISIBLE
                holder.question3.visibility = View.VISIBLE
                holder.answer3.visibility = View.VISIBLE
                holder.question4.visibility = View.VISIBLE
                holder.answer4.visibility = View.VISIBLE

                holder.answer1.text = "1) " + exercise.statment1 + ":"
                holder.answerTextView.text = exercise.answer1
                holder.question2.text = "2) " + exercise.statment2 + ":"
                holder.answer2.text = exercise.answer2
                holder.question3.text = "3) " + exercise.statment3 + ":"
                holder.answer3.text = exercise.answer3
                holder.question4.text = "4) " + exercise.statment4 + ":"
                holder.answer4.text = exercise.answer4
            }

            "2" -> {
                holder.question2.visibility = View.GONE
                holder.answer2.visibility = View.GONE
                holder.question3.visibility = View.GONE
                holder.answer3.visibility = View.GONE
                holder.question4.visibility = View.GONE
                holder.answer4.visibility = View.GONE
                holder.questionTextView.text = exercise.question
                holder.answerTextView.text = exercise.answer

                // Customize the layout for question type 2 as needed
            }

            "3" -> {
                holder.question2.visibility = View.GONE
                holder.answer2.visibility = View.GONE
                holder.question3.visibility = View.GONE
                holder.answer3.visibility = View.GONE
                holder.question4.visibility = View.GONE
                holder.answer4.visibility = View.GONE
                holder.questionTextView.text = exercise.question
                holder.answerTextView.text = exercise.answer
            }

            else -> {
                holder.question2.visibility = View.GONE
                holder.answer2.visibility = View.GONE
                holder.question3.visibility = View.GONE
                holder.answer3.visibility = View.GONE
                holder.question4.visibility = View.GONE
                holder.answer4.visibility = View.GONE
                holder.questionTextView.text = exercise.question
                holder.answerTextView.text = exercise.answer
                // Handle other question types here
            }
        }
    }
        override fun getItemCount(): Int {
            return exerciseList.size
        }

        // Add this method to update the data in the adapter
        fun setData(exerciseList: List<Exercise>) {
            this.exerciseList = exerciseList
            notifyDataSetChanged()
        }

        fun setQuizData(quizList: List<AllQuestion>) {
            quiz.clear()
            quiz.addAll(quizList)
            notifyDataSetChanged()
        }

    }

