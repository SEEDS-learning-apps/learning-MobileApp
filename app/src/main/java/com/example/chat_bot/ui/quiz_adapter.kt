package com.example.chat_bot.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat_bot.Activities.HomePage.ExerciseFragment
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.data.OpenEnded
import com.example.chat_bot.databinding.*
import com.example.chat_bot.utils.SessionManager
import com.example.chat_bot.utils.Time

class quiz_adapter (private val context: Context, val jkt: quiz_adapter.Callbackinter):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var idx: Int = 0
    private  var iterator: Int = 0
    private  var current_pos: Int = 1
    private var current_poss: Int = 1
    private var selectedId: Int =0
    lateinit var radioTXT: String
    private var correct_answers: Int = 0
    private var haveInto: Boolean = false
    var max: Int = 0
    lateinit var QuizArray: ArrayList <AllQuestion>
    var quiz = mutableListOf<AllQuestion>()
    var exerciseList: ArrayList<Exercise> = ArrayList()
    lateinit var session: SessionManager
    lateinit var topicName: String
    lateinit var subjectName: String
    val adapter = ExerciseHistoryAdapter(ExerciseFragment())
    var ansList: MutableList<String> = arrayListOf()
    private lateinit var message: String
    var Selected_ansList: MutableList<String> = arrayListOf()


    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        const val VIEW_TYPE_THREE = 3
        const val VIEW_TYPE_FOUR = 4
        const val VIEW_TYPE_FIVE = 5
    }


    private inner class IntroViewHolder(val binding: IntroItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, holder: IntroViewHolder) {
            val mcq = quiz[position]

            haveInto = true

            holder.binding.introTXT.text = mcq.introduction

            val url = mcq.file

            if (mcq.file.isNotEmpty() && mcq.file.isNotBlank())
                {
                    holder.binding.imageView.visibility = View.VISIBLE
                    Glide.with(context).load(url).into(holder.binding.imageView)
                }

                if (mcq.link!= null || mcq.link != "null" || mcq.link)
                {
                    holder.binding.introURL.visibility = View.VISIBLE
                    holder.binding.introURL.text = mcq.link
                    holder.binding.introURL.setOnClickListener {  }
                }
               holder.binding.introNXT.setOnClickListener {

                newIntro(holder, position)

            }
        }


    }

    private fun newIntro(holder: IntroViewHolder, position: Int)
    {
        current_pos++
        iterator++

            setQuiz(iterator, current_pos, QuizArray)
    }

    private inner class McqViewHolder(val binding: McqItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var radio: RadioButton
        lateinit var rbGroup: RadioGroup
        private var selectedPosition = RecyclerView.NO_POSITION

        fun bind(position: Int) {
            val mcq = quiz[position]

            binding.mcqSubmit.setBackgroundColor(
                ContextCompat.getColor(context, R.color.Activity_next_btn)
            )
            binding.mcqStatememntTV.text = mcq.mcqs
            binding.mcqOptOne.text = mcq.option1
            binding.mcqOptTwo.text = mcq.option2
            binding.mcqOptThree.text = mcq.option3
            binding.mcqOptFour.text = mcq.option4
            binding.mcqPosFeedbackTv.text = mcq.posFeedback
            binding.mcqNegFeedbackTv.text = mcq.negFeedback

            clearChecks()

            if (mcq.file.isNotEmpty() && mcq.file.isNotBlank()) {
                binding.imageView.visibility = View.VISIBLE
                Glide.with(context).load(mcq.file).into(binding.imageView)
            }

            binding.mcqSubmit.isEnabled = false

            if (haveInto == true) {
                max = QuizArray.size - 1
                current_poss = current_pos - 1
                binding.tvProgress.text = "$current_poss/$max"
                binding.progressBar.progress = current_poss
            } else {
                max = QuizArray.size
                binding.tvProgress.text = "$current_pos/$max"
                binding.progressBar.progress = current_pos
            }
            binding.progressBar.max = max

            rbGroup = binding.mcqRG

            rbGroup.setOnCheckedChangeListener { group, checkedId ->
                binding.mcqSubmit.isEnabled = true
                selectedId = checkedId
                radio = group.findViewById(selectedId)
                idx = rbGroup.indexOfChild(radio)

                Log.i("selectedtext", radio.text.toString())
                Log.i("selectedtext", radio.toString())
                Log.i("selectedtext", selectedId.toString())
                Log.i("mcq question", mcq.mcqs)
                Log.i("mcq answer", mcq.answer)

                radioTXT = radio.text.toString()

                val backgroundColor = if (selectedId == -1) {
                    ContextCompat.getColor(context, R.color.Activity_next_btn)
                } else {
                    // Use a darker color for selected state
                    ContextCompat.getColor(context, R.color.colorPrimaryblue)
                }
                binding.mcqSubmit.setBackgroundColor(backgroundColor)

                // Update the selected position when an option is selected
                selectedPosition = adapterPosition
            }

            binding.mcqSubmit.setOnClickListener {
                if (mcq.answer == "option1" && idx == 0) {
                    correct_answers++
                    binding.mcqNegFeedbackTv.visibility = View.GONE
                    binding.mcqPosFeedbackTv.visibility = View.VISIBLE
                    clearChecks()
                } else if (mcq.answer == "option2" && idx == 1) {
                    correct_answers++
                    binding.mcqNegFeedbackTv.visibility = View.GONE
                    binding.mcqPosFeedbackTv.visibility = View.VISIBLE
                } else if (mcq.answer == "option3" && idx == 2) {
                    correct_answers++
                    binding.mcqNegFeedbackTv.visibility = View.GONE
                    binding.mcqPosFeedbackTv.visibility = View.VISIBLE
                } else if (mcq.answer == "option4" && idx == 3) {
                    correct_answers++
                    binding.mcqNegFeedbackTv.visibility = View.GONE
                    binding.mcqPosFeedbackTv.visibility = View.VISIBLE
                } else {
                    binding.mcqNegFeedbackTv.visibility = View.VISIBLE
                    binding.mcqPosFeedbackTv.visibility = View.GONE
                }

                binding.mcqFeedbackCard.visibility = View.VISIBLE
                binding.mcqSubmit.visibility = View.GONE
                binding.feebackSubmit.visibility = View.VISIBLE

                binding.feebackSubmit.setOnClickListener {
                    new(this@McqViewHolder, position)

                    binding.mcqFeedbackCard.visibility = View.GONE
                    binding.feebackSubmit.visibility = View.GONE
                    binding.mcqSubmit.visibility = View.VISIBLE
                    binding.mcqNegFeedbackTv.visibility = View.GONE
                    binding.mcqPosFeedbackTv.visibility = View.GONE
                    clearChecks()
                }
            }

            // Check if the current position matches the selected position and set the radio button as checked
            if (adapterPosition == selectedPosition) {
                rbGroup.check(selectedId)
            }
        }

        private fun clearChecks() {
            binding.mcqOptOne.isChecked = false
            binding.mcqOptTwo.isChecked = false
            binding.mcqOptThree.isChecked = false
            binding.mcqOptFour.isChecked = false
        }
    }


    private fun new(holder: McqViewHolder, position: Int)
    {
        if (haveInto == true)
        {
            max = QuizArray.size -1
            current_poss = current_pos - 1
            holder.binding.tvProgress.text = "$current_poss/$max"
            holder.binding.progressBar.progress = current_poss
        }
        else
        {
            max = QuizArray.size
            holder.binding.tvProgress.text = "$current_pos/$max"
            holder.binding.progressBar.progress = current_pos
        }

        current_pos++
        iterator++
        var rbGroup: RadioGroup = holder.binding.mcqRG
        holder.binding.progressBar.max = max

        Log.d("loggg", current_pos.toString())
        Log.d("loggg", iterator.toString())

        if (rbGroup.checkedRadioButtonId == -1) {

            holder.binding.mcqSubmit.isEnabled = false
            rbGroup.clearCheck()

        }
        else
            holder.binding.mcqSubmit.isEnabled = true

        if(current_pos <= QuizArray!!.size)
        {
            setQuiz(iterator, current_pos, QuizArray)

        }
        else
        {
            positive_results_alert(QuizArray)

        }

        when {
            current_pos == QuizArray!!.size -> {
                holder.binding.mcqSubmit.text = "Finish"

            }
        }
    }

    private inner class TFViewHolder(val binding: TfItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, holder: TFViewHolder) {
            val tf = quiz[position]
            holder.binding.tfSubmit.setBackgroundColor(ContextCompat.getColor(context, R.color.Activity_next_btn))
            var ans  = tf.answer

            Log.i("True or False question", tf.question)
            Log.i("True or False answer", tf.answer)

            if (haveInto == true)
            {
                max = QuizArray.size -1
                current_poss = current_pos - 1
                holder.binding.tvProgress.text = "$current_poss/$max"
                holder.binding.progressBar.progress = current_poss
            }
            else
            {
                max = QuizArray.size
                holder.binding.tvProgress.text = "$current_pos/$max"
                holder.binding.progressBar.progress = current_pos
            }
            
            if (tf.file.isNotEmpty() && tf.file.isNotBlank())
            {
                holder.binding.imageView.visibility = View.VISIBLE
                Glide.with(context).load(tf.file).into(holder.binding.imageView)
            }

            holder.binding.tfStatememntTV.text = tf.question

            holder.binding.progressBar.max = max

            holder.binding.tfSubmit.isEnabled = false

            var rbGroup: RadioGroup = holder.binding.mcqRG

            if (rbGroup.checkedRadioButtonId == -1) {

                Toast.makeText(context, "please select your answer above", Toast.LENGTH_SHORT).show()
                holder.binding.tfSubmit.isEnabled = false
            }

            rbGroup.setOnCheckedChangeListener { group, checkedId ->
                holder.binding.tfSubmit.visibility = View.VISIBLE
                holder.binding.tfSubmit.isEnabled = true

                val selectedId = rbGroup.checkedRadioButtonId
                val radio: RadioButton = group.findViewById(checkedId)
                Log.i("selectedtext", radio.text.toString())
                radioTXT = radio.text.toString()
                // Toast.makeText(context, radioTXT, Toast.LENGTH_SHORT).show()

                // Update the background color based on the checked state
                val backgroundColor = if (selectedId == -1) {
                    ContextCompat.getColor(context, R.color.Activity_next_btn)
                } else {
                    // Use a darker color for selected state
                    ContextCompat.getColor(context, R.color.colorPrimaryblue)
                }
                holder.binding.tfSubmit.setBackgroundColor(backgroundColor)
            }

            holder.binding.tfSubmit.setOnClickListener{

                if (ans == "true" || ans == "TRUE" || ans == "True")
                {
                    if (radioTXT.lowercase()== "richtig"
                        || radioTXT.lowercase()== "αληθινό"
                        || radioTXT.lowercase()== "en realidad"
                        || radioTXT.lowercase() == "true")
                        {
                            correct_answers++

                            holder.binding.tfPosFeedbackTv.text = tf.posFeedback
                            holder.binding.tfNegFeedbackTv.visibility = View.GONE
                            holder.binding.tfPosFeedbackTv.visibility = View.VISIBLE

                    }
                    else {
                        holder.binding.tfNegFeedbackTv.text = tf.negFeedback
                        holder.binding.tfNegFeedbackTv.visibility = View.VISIBLE
                        holder.binding.tfPosFeedbackTv.visibility = View.GONE
                    }
                }
                else if (ans=="false"|| ans == "FALSE" || ans == "False")
                {
                    if (radioTXT.lowercase()== "ψευδές"
                        || radioTXT.lowercase()== "falsch"
                        || radioTXT.lowercase()== "falso"
                        || radioTXT.lowercase() == "false") {

                            correct_answers++

                        holder.binding.tfPosFeedbackTv.text = tf.posFeedback
                        holder.binding.tfNegFeedbackTv.visibility = View.GONE
                        holder.binding.tfPosFeedbackTv.visibility = View.VISIBLE
                    }
                    else
                    {
                        holder.binding.tfNegFeedbackTv.text = tf.negFeedback
                        holder.binding.tfNegFeedbackTv.visibility = View.VISIBLE
                        holder.binding.tfPosFeedbackTv.visibility = View.GONE
                    }
                }

                holder.binding.tfFeedbackCard.visibility = View.VISIBLE
                holder.binding.tfSubmit.visibility = View.GONE
                holder.binding.feebackSubmit.visibility = View.VISIBLE

                holder.binding.feebackSubmit.setOnClickListener {
                new_trf(holder, position)
                holder.binding.tfFeedbackCard.visibility = View.GONE
                holder.binding.feebackSubmit.visibility = View.GONE
                holder.binding.tfSubmit.visibility = View.VISIBLE
                    holder.binding.tfOptOne.isChecked = false
                    holder.binding.tfOptTwo.isChecked = false

            }

            }

        }
    }

    private fun new_trf(holder: TFViewHolder, position: Int)
    {
        if (haveInto == true)
        {
            max = QuizArray.size -1
            current_poss = current_pos - 1
            holder.binding.tvProgress.text = "$current_poss/$max"
            holder.binding.progressBar.progress = current_poss
        }
        else
        {
            max = QuizArray.size
            holder.binding.tvProgress.text = "$current_pos/$max"
            holder.binding.progressBar.progress = current_pos
        }

        current_pos++
        iterator++
        var rbGroup: RadioGroup = holder.binding.mcqRG
        holder.binding.progressBar.max = max

        if (rbGroup.checkedRadioButtonId == -1) {

            Toast.makeText(context, "please select your answer", Toast.LENGTH_SHORT).show()
            holder.binding.tfSubmit.isEnabled = false
        }
        else
            holder.binding.tfSubmit.isEnabled = true

        if(current_pos <= QuizArray!!.size)
        {
            setQuiz(iterator, current_pos, QuizArray)

        }
        else{
            positive_results_alert(QuizArray)
        }

        when {
            current_pos == QuizArray!!.size -> {

                holder.binding.tfSubmit.text = "Finish"
            }

        }
    }


    private inner class MatchViewHolder(val binding: ActivityMatchingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, holder: MatchViewHolder) {
            val matchingActivity = quiz[position]

            binding.matchingSubmit.setBackgroundColor(
                ContextCompat.getColor(context, R.color.Activity_next_btn)
            )

            if (ansList.size>0)
            {
                ansList.clear()
            }

            if (matchingActivity.answer5.isNotEmpty() || matchingActivity.answer5.isNotBlank())
            {

                ansList.addAll(listOf(quiz[position].answer1,quiz[position].answer2,
                    quiz[position].answer3, quiz[position].answer4, quiz[position].answer5 ))
                ansList.toSet().toList()
                Log.d("ansList", ansList.size.toString())

                if (ansList.size == 5)
                {

                    holder.binding.activyText.text = matchingActivity.question
                    holder.binding.st1TXT.text = matchingActivity.statement1
                    holder.binding.st2TXT.text = matchingActivity.statement2
                    holder.binding.st3TXT.text = matchingActivity.statement3
                    holder.binding.st4TXT.text = matchingActivity.statement4
                    holder.binding.st5TXT.text = matchingActivity.statement5

                }
            }

          else  if (matchingActivity.answer5.isBlank() || matchingActivity.answer5.isEmpty()
                || matchingActivity.answer5 != "")
            {

                ansList.addAll(listOf(quiz[position].answer1,quiz[position].answer2,
                    quiz[position].answer3, quiz[position].answer4))
                ansList.toSet().toList()
                Log.d("ansList", ansList.size.toString())

                if(ansList.size == 4)
                {  holder.binding.activyText.text = matchingActivity.question
                    holder.binding.st1TXT.text = matchingActivity.statement1
                    holder.binding.st2TXT.text = matchingActivity.statement2
                    holder.binding.st3TXT.text = matchingActivity.statement3
                    holder.binding.st4TXT.text = matchingActivity.statement4
                    holder.binding.st5.visibility = View.GONE
                }
            }

           else if (matchingActivity.answer4.isBlank() || matchingActivity.answer4.isEmpty()
                || matchingActivity.answer4 != "")
            {
                ansList.addAll(listOf(quiz[position].answer1,quiz[position].answer2,
                    quiz[position].answer3))
                Log.d("ansList", ansList.size.toString())

                if(ansList.size == 3)
                {

                    holder.binding.activyText.text = matchingActivity.question
                    holder.binding.st1TXT.text = matchingActivity.statement1
                    holder.binding.st2TXT.text = matchingActivity.statement2
                    holder.binding.st3TXT.text = matchingActivity.statement3
                    holder.binding.st4.visibility = View.GONE
                    holder.binding.st5.visibility = View.GONE

                }
            }

          else if (matchingActivity.answer3.isBlank() || matchingActivity.answer3.isEmpty()
                || matchingActivity.answer3 != "")
            {
                ansList.addAll(listOf(quiz[position].answer1,quiz[position].answer2))
                Log.d("ansList", ansList.size.toString())

                 if(ansList.size == 2)
                 {

                    holder.binding.activyText.text = matchingActivity.question
                    holder.binding.st1TXT.text = matchingActivity.statement1
                    holder.binding.st2TXT.text = matchingActivity.statement2
                    holder.binding.st3.visibility = View.GONE
                    holder.binding.st4.visibility = View.GONE
                    holder.binding.st5.visibility = View.GONE

                 }
            }

            else if (matchingActivity.answer2.isBlank() || matchingActivity.answer2.isEmpty()
                || matchingActivity.answer2 != "")
            {
                ansList.addAll(listOf(quiz[position].answer1))
                Log.d("ansList", ansList.size.toString())

                if (ansList.size == 1)
                {
                    holder.binding.activyText.text = matchingActivity.question
                    holder.binding.st1TXT.text = matchingActivity.statement1
                    holder.binding.st2.visibility = View.GONE
                    holder.binding.st3.visibility = View.GONE
                    holder.binding.st4.visibility = View.GONE
                    holder.binding.st5.visibility = View.GONE
                }
            }

             if (matchingActivity.file.isNotBlank() && matchingActivity.file.isNotEmpty())
            {
                holder.binding.imageView.visibility = View.VISIBLE
                Glide.with(context).load(matchingActivity.file).into(holder.binding.imageView)
            }

            holder.binding.matchingPosFeedbackTv.text = matchingActivity.posFeedback
            holder.binding.matchingNegFeedbackTv.text = matchingActivity.negFeedback

            if (haveInto == true)
            {
                max = QuizArray.size -1
                current_poss = current_pos - 1
                holder.binding.tvProgress.text = "$current_poss/$max"
                holder.binding.progressBar.progress = current_poss
            }
            else
            {
                max = QuizArray.size
                holder.binding.tvProgress.text = "$current_pos/$max"
                holder.binding.progressBar.progress = current_pos
            }

            holder.binding.progressBar.max = max
            holder.binding.matchingSubmit.isEnabled = false

            setAnsSpinner(ansList, binding)

            holder.binding.matchingSubmit.setOnClickListener {

                Selected_ansList
                if (ansList == Selected_ansList)
                {
                    correct_answers++
                    holder.binding.matchingNegFeedbackTv.visibility = View.GONE
                    holder.binding.matchingPosFeedbackTv.visibility = View.VISIBLE
                }

                else if (ansList != Selected_ansList)
                {
                    holder.binding.matchingNegFeedbackTv.visibility = View.VISIBLE
                    holder.binding.matchingPosFeedbackTv.visibility = View.GONE
                }

                holder.binding.matchingFeedbackCard.visibility = View.VISIBLE
                holder.binding.matchingSubmit.visibility = View.GONE
                holder.binding.feebackSubmit.visibility = View.VISIBLE

                holder.binding.feebackSubmit.setOnClickListener { newMatching(holder, position)   }

            }

        }

        private fun setAnsSpinner(ansList: MutableList<String>, binding: ActivityMatchingBinding){
            val ans1_spinner = binding.st1Sp
            val ans2_spinner = binding.st2Sp
            val ans3_spinner = binding.st3Sp
            val ans4_spinner = binding.st4Sp
            val ans5_spinner = binding.st5Sp

            var s_Ans1: String
            var s_Ans2: String
            var s_Ans3: String
            var s_Ans4: String
            var s_Ans5: String

            ansList.toSet().toList()

            if (ans1_spinner != null) {
                val adapter = ArrayAdapter(context, R.layout.ans_dropdwn, ansList)
                ans1_spinner.setAdapter(adapter)

                adapter.notifyDataSetChanged()

                ans1_spinner.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        s_Ans1 = ans1_spinner.text.toString()
                        Selected_ansList.addAll(listOf(s_Ans1))
                        Log.d("Select answer", "Selected ${Selected_ansList.size}")

                        if (ansList.size== Selected_ansList.size)
                        {
                            binding.matchingSubmit.isEnabled = true
                            binding.matchingSubmit.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorPrimaryblue))
                        }

                        adapter.notifyDataSetChanged()

                    }

            }

            if (ans2_spinner != null) {
                val adapter = ArrayAdapter(context, R.layout.ans_dropdwn, ansList)
                ans2_spinner.setAdapter(adapter)
                adapter.notifyDataSetChanged()


                ans2_spinner.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        s_Ans2 = ans2_spinner.text.toString()
                        Selected_ansList.addAll(listOf(s_Ans2))

                        if (ansList.size== Selected_ansList.size)
                        {
                            binding.matchingSubmit.isEnabled = true
                            binding.matchingSubmit.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorPrimaryblue)
                            )

                        }

                        adapter.notifyDataSetChanged()

                    }

            }

            if (ans3_spinner != null) {
                val adapter = ArrayAdapter(context, R.layout.ans_dropdwn, ansList)
                ans3_spinner.setAdapter(adapter)
                adapter.notifyDataSetChanged()

                ans3_spinner.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        s_Ans3 = ans3_spinner.text.toString()
                        Selected_ansList.addAll(listOf(s_Ans3))

                        if (ansList.size== Selected_ansList.size)
                        {
                            binding.matchingSubmit.isEnabled = true
                            binding.matchingSubmit.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorPrimaryblue)
                            )
                        }

                        adapter.notifyDataSetChanged()

                    }
            }

            if (ans4_spinner != null) {
                val adapter = ArrayAdapter(context, R.layout.ans_dropdwn, ansList)
                ans4_spinner.setAdapter(adapter)
                adapter.notifyDataSetChanged()

                ans4_spinner.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        s_Ans4 = ans4_spinner.text.toString()
                        Selected_ansList.addAll(listOf(s_Ans4))

                        if (ansList.size== Selected_ansList.size)
                        {
                            binding.matchingSubmit.isEnabled = true
                            binding.matchingSubmit.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorPrimaryblue)
                            )
                        }

                        adapter.notifyDataSetChanged()

                    }

            }

            if (ans5_spinner != null) {
                val adapter = ArrayAdapter(context, R.layout.ans_dropdwn, ansList)
                ans5_spinner.setAdapter(adapter)
                adapter.notifyDataSetChanged()

                ans5_spinner.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id ->

                        s_Ans5 = ans4_spinner.text.toString()
                        Selected_ansList.addAll(listOf(s_Ans5))

                        if (ansList.size== Selected_ansList.size)
                        {
                            binding.matchingSubmit.isEnabled = true
                            binding.matchingSubmit.setBackgroundColor(
                                ContextCompat.getColor(context, R.color.colorPrimaryblue)
                            )
                        }

                        adapter.notifyDataSetChanged()

                    }
            }

        }
    }

    private fun newMatching(holder: MatchViewHolder, position: Int)
    {
        current_pos++
        iterator++
        if (haveInto == true)
        {
            max = QuizArray.size -1
            current_poss = current_pos - 1
            holder.binding.tvProgress.text = "$current_poss/$max"
            holder.binding.progressBar.progress = current_poss
        }
        else
        {
            max = QuizArray.size
            holder.binding.tvProgress.text = "$current_pos/$max"
            holder.binding.progressBar.progress = current_pos
        }

        holder.binding.progressBar.max = max



        if(current_pos <= QuizArray!!.size)
        {
            setQuiz(iterator, current_pos, QuizArray)

        }
        else
        {
            positive_results_alert(QuizArray)

        }

        when {
            current_pos == QuizArray!!.size -> {
                holder.binding.matchingSubmit.text = "Finish"

            }

        }
    }

    private inner class OpenEndedViewHolder(val binding: ActivityOpenEndedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, holder: OpenEndedViewHolder) {
            val matchingActivity = quiz[position]

            holder.binding.opStatememntTV.text = matchingActivity.question

            if (haveInto == true)
            {
                max = QuizArray.size -1
                current_poss = current_pos - 1
                holder.binding.tvProgress.text = "$current_poss/$max"
                holder.binding.progressBar.progress = current_poss
            }
            else
            {
                max = QuizArray.size
                holder.binding.tvProgress.text = "$current_pos/$max"
                holder.binding.progressBar.progress = current_pos
            }

            holder.binding.progressBar.max = max

            holder.binding.btnopenEndedsubmit.setOnClickListener {
                val answer = holder.binding.opAnsV.text.toString()
                newOpenEnded(holder, position)

                Log.d("answers", answer)
                postSubmission(matchingActivity, answer)
            }

        }

    }

    private fun postSubmission(matchingActivity: AllQuestion, answer: String) {

        val user = session.getUserDetails()
        var teacherID = matchingActivity.userId
        val username = user.get("name")
        val answer = answer
        val timeStamp = Time.timeStamp().toString()
        val questionid = matchingActivity._id

        val openEnded = OpenEnded(questionid, timeStamp, answer, username.toString(), teacherID)

        Log.d("answer", answer)

        jkt.submitAnswerCallback(openEnded)

    }

    private fun newOpenEnded(holder: OpenEndedViewHolder, position: Int)
    {
        current_pos++
        iterator++

        holder.binding.opAnsV.text.clear()
        if (haveInto == true)
        {
            max = QuizArray.size -1
            current_poss = current_pos - 1
            holder.binding.tvProgress.text = "$current_poss/$max"
            holder.binding.progressBar.progress = current_poss
        }
        else
        {
            max = QuizArray.size
            holder.binding.tvProgress.text = "$current_pos/$max"
            holder.binding.progressBar.progress = current_pos
        }

        holder.binding.progressBar.max = max


        if(current_pos <= QuizArray!!.size)
        {
            setQuiz(iterator, current_pos, QuizArray)

        }
        else
        {
            positive_results_alert(QuizArray)

        }

        when {
            current_pos == QuizArray!!.size -> {

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        session = SessionManager(this.context)

        return if (viewType == VIEW_TYPE_TWO) {
            return McqViewHolder(
                McqItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        else if (viewType == VIEW_TYPE_ONE)
        {
            return IntroViewHolder(
                IntroItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }

        else if (viewType == VIEW_TYPE_THREE)
        {
            return TFViewHolder(
                TfItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }

        else if (viewType == VIEW_TYPE_FIVE)
        {
            return OpenEndedViewHolder(
                ActivityOpenEndedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }

        else
            MatchViewHolder(
                ActivityMatchingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (quiz[position].q_type == VIEW_TYPE_TWO) {
            (holder as McqViewHolder).bind(position)
        }
        else if (quiz[position].q_type == VIEW_TYPE_THREE)
        {
            (holder as TFViewHolder).bind(position, holder)
        }
        else if (quiz[position].q_type == VIEW_TYPE_FOUR)
        {
            (holder as MatchViewHolder).bind(position, holder)
        }
        else if (quiz[position].q_type == VIEW_TYPE_FIVE)
        {
            (holder as OpenEndedViewHolder).bind(position, holder)
        }
        else {
            (holder as IntroViewHolder).bind(position, holder)
        }
    }

    override fun getItemCount(): Int {
        return quiz.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("get quiz item", quiz[position].q_type.toString())
        return quiz[position].q_type
    }

    fun setQuizList(az: ArrayList<AllQuestion>, topicName: String) {
        setQuiz(iterator, current_pos, az)
        this.QuizArray = az
        this.topicName = topicName

    }

    private fun setQuiz(iterator: Int, currentPos: Int, quizlists: ArrayList<AllQuestion>) {

        quiz = quizlists.subList(iterator,currentPos).toMutableList() as ArrayList<AllQuestion>

        Log.d("Set quiz", quiz.toString())

        notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    fun positive_results_alert(az: ArrayList<AllQuestion>)
    {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.positive_feedback,null)
        val Score = view.findViewById<TextView>(R.id.Results_score_tv)

        if (haveInto== true)
        {
            message ="done is"

            var size: Int = az.size
            size -=1
            Score.text = "You got"+" "+ correct_answers.toString() +" "+ "out of" +" " + size

            session.saveTotalScore(size.toString())
            session.saveObtainedScore(correct_answers.toString())
        }
        else
        {
            Score.text = "You got"+" "+ correct_answers.toString() +" "+ "out of" +" " + az.size

            session.saveObtainedScore(correct_answers.toString())
            session.saveTotalScore(az.size.toString())

            message =
                "done is"
        }

        saveScores(correct_answers, az.size)
        val  button = view.findViewById<Button>(R.id.Results_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {

            builder.dismiss()

            val intent = Intent(context, MainActivity::class.java)
             intent.putExtra("Total score",  message)
            intent.putExtra("Obtained", correct_answers)
            context.startActivity(intent)
            (context as Activity).finish()
            Log.i("Results btn clicked", intent.toString())

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
        session.quizDone("done")
        adapter.notifyDataSetChanged()

        if (!builder.isShowing)
        {
            Toast.makeText(context, "no builder", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveScores(correctAnswers: Int, totalQues: Int) {

        exerciseList = session.readListFromPref(this.context) as ArrayList<Exercise>

        val timeStamp = Time.timeStamp()
        this.subjectName = session.get_subject()

        exerciseList.add(Exercise(subjectName,topicName, correctAnswers.toString(), totalQues.toString(), timeStamp))

        session.writeListInPref(this.context,exerciseList)

        Log.d("Save score", exerciseList.size.toString())
    }

    interface Callbackinter {

        fun submitAnswerCallback(openEnded: OpenEnded)
        fun quizDonez()

    }

}