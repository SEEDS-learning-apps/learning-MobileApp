package com.example.chat_bot.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.Data
import com.example.chat_bot.data.Mcqs
import com.example.chat_bot.databinding.McqItemBinding


class McqAdapter (val context: Context) : RecyclerView.Adapter<McqAdapter.mcqViewholder>() {

    lateinit var az: ArrayList <Mcqs>
    private  var iterator: Int = 0
    private  var current_pos: Int = 1
    var message: String ?= null
    private var correct_answers: Int = 0
    lateinit var radioTXT: String
    private var selectedId: Int =0
    private var op1 = 2131362127
    private var op2 = 2131362129
    private var op3 = 2131362128
    private var op4 = 2131362126
    var haveTFS: Boolean = false
    lateinit var filterd_trufalses: ArrayList<Data>
    var mcqs = mutableListOf<Mcqs>()

    fun setMcqList(az: ArrayList<Mcqs>, haveTFS: Boolean, filterd_trufalses: ArrayList<Data>) {
        setMcqs(iterator, current_pos, az)
        this.az = az
        this.filterd_trufalses = filterd_trufalses
        this.haveTFS = haveTFS

    }

    private fun setMcqs(iterator : Int, current_pos: Int, AY: ArrayList<Mcqs>) {

        mcqs =  AY.subList(iterator,current_pos).toMutableList()

        Log.d("loggg", mcqs.toString())
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mcqViewholder {
        //    val inflater =
//        val bindings =
        return mcqViewholder(
            McqItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBindViewHolder(holder: mcqViewholder, position: Int) {

        val mcq = mcqs[position]
        holder.binding.mcqStatememntTV.text = mcq.mcqs
        holder.binding.mcqOptOne.text = mcq.option1
        holder.binding.mcqOptTwo.text = mcq.option2
        holder.binding.mcqOptThree.text = mcq.option3
        holder.binding.mcqOptFour.text = mcq.option4
        holder.binding.mcqPosFeedbackTv.text = mcq.posFeedback
        holder.binding.mcqNegFeedbackTv.text = mcq.negFeedback
        holder.binding.progressBar.progress = current_pos
        holder.binding.mcqSubmit.isEnabled = false

        var max: Int
        max = az.size
        holder.binding.tvProgress.text = "$current_pos" + "/" + max

        holder.binding.progressBar.max = max




        var rbGroup: RadioGroup = holder.binding.mcqRG

        if (rbGroup.checkedRadioButtonId == -1) {

            Toast.makeText(context, "please select your answer", Toast.LENGTH_SHORT).show()
            holder.binding.mcqSubmit.isEnabled = false
        }


            rbGroup.setOnCheckedChangeListener { group, checkedId ->
                holder.binding.mcqSubmit.isEnabled = true
            selectedId = rbGroup.checkedRadioButtonId
            var radio: RadioButton = group.findViewById(checkedId)
            Log.e("selectedtext", radio.text.toString())
            radioTXT= radio.text.toString()
            Toast.makeText(context, radioTXT, Toast.LENGTH_SHORT).show()

        }

        holder.binding.mcqSubmit.setOnClickListener{
            if (mcq.answer == "option1" && selectedId == op1){
                correct_answers++
                holder.binding.mcqNegFeedbackTv.visibility = View.GONE
                holder.binding.mcqPosFeedbackTv.visibility = View.VISIBLE
            }
            else if (mcq.answer == "option2" && selectedId == op2){
                correct_answers++
                holder.binding.mcqNegFeedbackTv.visibility = View.GONE
                holder.binding.mcqPosFeedbackTv.visibility = View.VISIBLE
            }
            else if (mcq.answer == "option3" && selectedId == op3){
                correct_answers++
                holder.binding.mcqNegFeedbackTv.visibility = View.GONE
                holder.binding.mcqPosFeedbackTv.visibility = View.VISIBLE
            }
            else if (mcq.answer == "option4" && selectedId == op4){
                correct_answers++
                holder.binding.mcqNegFeedbackTv.visibility = View.GONE
                holder.binding.mcqPosFeedbackTv.visibility = View.VISIBLE
            }
            else
            {
                holder.binding.mcqNegFeedbackTv.visibility = View.VISIBLE
                holder.binding.mcqPosFeedbackTv.visibility = View.GONE
            }

            holder.binding.mcqFeedbackCard.visibility = View.VISIBLE
            holder.binding.mcqSubmit.visibility = View.GONE
            holder.binding.feebackSubmit.visibility = View.VISIBLE

            holder.binding.feebackSubmit.setOnClickListener {
                holder.binding.mcqFeedbackCard.visibility = View.GONE
                holder.binding.feebackSubmit.visibility = View.GONE
                holder.binding.mcqSubmit.visibility = View.VISIBLE
            }

        }

    }


    fun positive_results_alert(az: ArrayList<Mcqs>)
    {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.positive_feedback,null)
        val Score = view.findViewById<TextView>(R.id.Results_score_tv)
        Score.text = "You got"+" "+ correct_answers.toString() +" "+ "out of" +" " + az.size
        val  button = view.findViewById<Button>(R.id.Results_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {
            val intent = Intent(context,MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()
            Log.i("Results btn clicked", intent.toString())

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()

        if (!builder.isShowing)
        {
            Toast.makeText(context, "no builder", Toast.LENGTH_SHORT).show()
        }

        }


    override fun getItemCount(): Int {
            return mcqs.size
        }

      inner class mcqViewholder(val binding: McqItemBinding) : RecyclerView.ViewHolder(binding.root) {

        }



}



