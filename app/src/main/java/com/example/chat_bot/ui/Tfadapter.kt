package com.example.chat_bot.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.Data

import com.example.chat_bot.databinding.TfItemBinding

class Tfadapter(val context: Context) : RecyclerView.Adapter<Tfadapter.tfViewholder>() {

    lateinit var az: ArrayList <Data>
    private  var iterator: Int = 0
    private  var current_pos: Int = 1
    var message: String ?= null
    private var correct_answers: Int = 0
    private var correct_mcqs: Int = 0
    lateinit var radioTXT: String
    private var total_mcqs: Int = 0

    var tfs = mutableListOf<Data>()

    fun setMcqList(az: ArrayList<Data>, correct_answers: Int, totale_mcq: Int) {
        setTfs(iterator, current_pos, az)
        this.az = az
        correct_mcqs = correct_answers
        this.total_mcqs = totale_mcq

    }

    private fun setTfs(iterator: Int, current_pos: Int, AY: ArrayList<Data>) {

        tfs =  AY.subList(iterator,current_pos)

        Log.d("loggg", tfs.toString())
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Tfadapter.tfViewholder {
        return tfViewholder(
            TfItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: Tfadapter.tfViewholder, position: Int) {
        val tf = tfs[position]
        var que: String = ""
        var ans : String = ""


        ans.lowercase()
        for (item in tfs)
        {
                que  = item.question
                ans  = item.answer
        }


        holder.binding.tfStatememntTV.text = que
        holder.binding.tfOptOne.text = "True"
        holder.binding.tfOptTwo.text = "False"

//        holder.binding.tfPosFeedbackTv.text = tf.posFeedback
//        holder.binding.tfNegFeedbackTv.text = tf.negFeedback
        holder.binding.progressBar.progress = current_pos

        holder.binding.tfSubmit.isEnabled = false
        var max = holder.binding.progressBar.max
        max = az.size + total_mcqs

        holder.binding.tvProgress.text = max.toString()
        holder.binding.tvProgress.text = "$current_pos" + "/" + max

        Log.d("tf", ans)
        Toast.makeText(context, ans, Toast.LENGTH_SHORT).show()

        var rbGroup: RadioGroup = holder.binding.mcqRG

        if (rbGroup.checkedRadioButtonId == -1) {

            Toast.makeText(context, "please select your answer", Toast.LENGTH_SHORT).show()
            holder.binding.tfSubmit.isEnabled = false
        }

        rbGroup.setOnCheckedChangeListener { group, checkedId ->
            holder.binding.tfSubmit.isEnabled = true
            var selectedId = rbGroup.checkedRadioButtonId
            var radio: RadioButton = group.findViewById(checkedId)
            Log.e("selectedtext", radio.text.toString())
            radioTXT= radio.text.toString()
            Toast.makeText(context, radioTXT, Toast.LENGTH_SHORT).show()

//            if (text == mcq.answer) {
//               // flag = "correct"
//             //   Toast.makeText(context, "right answer", Toast.LENGTH_SHORT).show()
//                holder.binding.mcqSubmit.isEnabled = true
//                correct_answers++
//
//                //callbackInterface.passResultCallback("Your message")
//
//
//
//            } else {
//              //  Toast.makeText(context, "wrong answer!!", Toast.LENGTH_SHORT).show()
//
//            }
        }

        holder.binding.tfSubmit.setOnClickListener{
            if (radioTXT.lowercase() == ans){
                correct_answers++
                //Toast.makeText(context, "Right answer", Toast.LENGTH_SHORT).show()
            }
            else if (radioTXT != ans)
            {
                //holder.binding.tfNegFeedbackTv.visibility = View.VISIBLE
                //holder.binding.tfPosFeedbackTv.visibility = View.GONE
            }

//            holder.binding.tfFeedbackCard.visibility = View.VISIBLE
            holder.binding.tfSubmit.visibility = View.GONE
            new(holder, position)
//            holder.binding.feebackSubmit.visibility = View.VISIBLE

//            holder.binding.feebackSubmit.setOnClickListener {
//                new(holder, position)
//                holder.binding.tfFeedbackCard.visibility = View.GONE
//                holder.binding.feebackSubmit.visibility = View.GONE
//                holder.binding.tfSubmit.visibility = View.VISIBLE
//            }
        }
    }

    private fun new(holder: Tfadapter.tfViewholder, position: Int)
    {
        var rbGroup: RadioGroup = holder.binding.mcqRG
        holder.binding.progressBar.progress = current_pos
        holder.binding.tvProgress.text = "$current_pos" + "/" + holder.binding.progressBar.max
        current_pos++
        iterator++

        if (rbGroup.checkedRadioButtonId == -1) {

            Toast.makeText(context, "please select your answer", Toast.LENGTH_SHORT).show()
            holder.binding.tfSubmit.isEnabled = false

        }
        else
            holder.binding.tfSubmit.isEnabled = true



        if(current_pos <= az!!.size)
        {

            setTfs(iterator, current_pos, az)

        }
        else{//Toast.makeText(context, "quiz khtm", Toast.LENGTH_SHORT).show()
            //fire_alert()
            positive_results_alert(az)
        }

        when {
            current_pos == az!!.size -> {
                holder.binding.tfSubmit.text = "Finish"
                //   Toast.makeText(context, "quiz done", Toast.LENGTH_SHORT).show()

            }

        }
    }

    fun positive_results_alert(az: ArrayList<Data>)
    {
        val score: Int = correct_mcqs+correct_answers
        val total : Int= az.size+total_mcqs
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.positive_feedback,null)
        val Score = view.findViewById<TextView>(R.id.Results_score_tv)
        Score.text = "You got $score out of $total"
        val  button = view.findViewById<Button>(R.id.Results_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as Activity).finish()

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()

        if (!builder.isShowing)
        {
            Toast.makeText(context, "no builder", Toast.LENGTH_SHORT).show()
        }



    }

    override fun getItemCount(): Int {
        return tfs.size
    }

    inner class tfViewholder(val binding: TfItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}