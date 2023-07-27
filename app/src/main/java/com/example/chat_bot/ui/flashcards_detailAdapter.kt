import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.ui.quiz_adapter

class CustomCardAdapter(private var exercise: Exercise) : RecyclerView.Adapter<CustomCardAdapter.ViewHolder>() {

    var position: Int? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.question)
        val questionTextView: TextView = itemView.findViewById(R.id.questionTextView)
        val answerTextView: TextView = itemView.findViewById(R.id.answerTextView)
        val answer1: TextView = itemView.findViewById(R.id.answer)
        val question2: TextView = itemView.findViewById(R.id.matchquestion2)
        val question3: TextView = itemView.findViewById(R.id.matchquestion3)
        val question4: TextView = itemView.findViewById(R.id.matchquestion4)
        val answer2: TextView = itemView.findViewById(R.id.matchAnswer2)
        val answer3: TextView = itemView.findViewById(R.id.matchAnswer3)
        val answer4: TextView = itemView.findViewById(R.id.matchAnswer4)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val introURL: TextView = itemView.findViewById(R.id.introURL)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flashcards_detailitem, parent, false)
        return ViewHolder(view)
    }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val question = exercise.questions[position]
            holder.questionTextView.text = question.question
            holder.answerTextView.text = question.answer

            holder.question2.visibility = View.GONE
            holder.answer2.visibility = View.GONE
            holder.question3.visibility = View.GONE
            holder.answer3.visibility = View.GONE
            holder.question4.visibility = View.GONE
            holder.answer4.visibility = View.GONE

            if (exercise.questionType == "4" && question.statment1?.isNotBlank() == true && question.statment2?.isNotBlank() == true &&
                question.statment3?.isNotBlank() == true && question.statment4?.isNotBlank() == true
            ) {
                // Show additional views for type "4" questions
                holder.question2.visibility = View.VISIBLE
                holder.answer2.visibility = View.VISIBLE
                holder.question3.visibility = View.VISIBLE
                holder.answer3.visibility = View.VISIBLE
                holder.question4.visibility = View.VISIBLE
                holder.answer4.visibility = View.VISIBLE

                holder.answer1.text = "1) " + question.statment1 + ":"
                holder.answerTextView.text = question.answer1
                holder.question2.text = "2) " + question.statment2 + ":"
                holder.answer2.text = question.answer2
                holder.question3.text = "3) " + question.statment3 + ":"
                holder.answer3.text = question.answer3
                holder.question4.text = "4) " + question.statment4 + ":"
                holder.answer4.text = question.answer4
            }else {
                    // Hide additional views for non-type 4 questions
                    holder.question2.visibility = View.GONE
                    holder.answer2.visibility = View.GONE
                    holder.question3.visibility = View.GONE
                    holder.answer3.visibility = View.GONE
                    holder.question4.visibility = View.GONE
                    holder.answer4.visibility = View.GONE
                }

            val url = question.file

            if (question.file?.isNotEmpty() == true && question.file.isNotBlank()) {
                holder.imageView.visibility = View.VISIBLE
                Glide.with(holder.imageView.context).load(url).into(holder.imageView)
            } else {
                holder.imageView.visibility = View.GONE
            }

            if (question.link != null && question.link != "null") {
                holder.question.text = "Click the link below to read the text and continue"
                holder.questionTextView.text = question.link
                holder.questionTextView.setTextColor(Color.BLUE)
                holder.questionTextView.paintFlags = holder.questionTextView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                holder.answer1.visibility = View.GONE

                holder.questionTextView.setTypeface(null, Typeface.NORMAL)

                holder.questionTextView.setOnClickListener {
                    val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(question.link))
                    holder.itemView.context.startActivity(urlIntent)
                }
            }  else {
                holder.introURL.visibility = View.GONE
            }
        }


        override fun getItemCount(): Int {
            return exercise.questions.size
        }

    }

