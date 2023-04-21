package com.example.chat_bot.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.Activities.activity.TruefalseActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.Data
import com.example.chat_bot.databinding.McqItemBinding
import com.example.chat_bot.data.Mcqss


class McqAdapter (val context: Context) : RecyclerView.Adapter<McqAdapter.mcqViewholder>() {

    lateinit var az: ArrayList <Mcqss>
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
    private var IsFinished: Boolean = false
    lateinit var filterd_trufalses: ArrayList<Data>
    private var mSelectedOptionPosition: Int = 0


   // private var mQuestionList: ArrayList<Mcqss>? = null
    // var mcqs:ArrayList<Mcqss> ?= null
    var mcqs = mutableListOf<Mcqss>()




    //  var onItemClick: ((Mcqss) -> Unit) ?= null

    //private var options: List<String> = listOf(question.option1, question.option2, question.option3, question.option4)

    fun setMcqList(az: ArrayList<Mcqss>, haveTFS: Boolean, filterd_trufalses: ArrayList<Data>) {
        setMcqs(iterator, current_pos, az)
        this.az = az
        this.filterd_trufalses = filterd_trufalses
        this.haveTFS = haveTFS
//        mcqs =  az.subList(iterator,current_pos).toMutableList()
//        Log.d("loggg", mcqs.toString())
//        notifyDataSetChanged()

//        get_mcq_set()


      //  mcqs = az.subList(0, 1).toMutableList()
        //Zarori
//        mcqs =  az.subList(0,current_pos).toMutableList()
//        Log.d("loggg", mcqs.toString())
//        notifyDataSetChanged()
        //   var list_size =  this.mcqs.size
    }

    private fun setMcqs(iterator : Int, current_pos: Int, AY: ArrayList<Mcqss>) {

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
                new(holder, position)
                holder.binding.mcqFeedbackCard.visibility = View.GONE
                holder.binding.feebackSubmit.visibility = View.GONE
                holder.binding.mcqSubmit.visibility = View.VISIBLE
            }

        }


















//        holder.binding.mcqSubmit.setOnClickListener{
//            if (radioTXT == mcq.answer){
//                correct_answers++
//                holder.binding.mcqNegFeedbackTv.visibility = View.GONE
//                holder.binding.mcqPosFeedbackTv.visibility = View.VISIBLE
//            }
//            else if (radioTXT != mcq.answer)
//            {
//                holder.binding.mcqNegFeedbackTv.visibility = View.VISIBLE
//                holder.binding.mcqPosFeedbackTv.visibility = View.GONE
//            }
//
//            holder.binding.mcqFeedbackCard.visibility = View.VISIBLE
//            holder.binding.mcqSubmit.visibility = View.GONE
//            holder.binding.feebackSubmit.visibility = View.VISIBLE
//
//               holder.binding.feebackSubmit.setOnClickListener {
//                new(holder, position)
//                holder.binding.mcqFeedbackCard.visibility = View.GONE
//                holder.binding.feebackSubmit.visibility = View.GONE
//                holder.binding.mcqSubmit.visibility = View.VISIBLE
//            }
//        }

            //new(holder, position)}

//        if(rbGroup.checkedRadioButtonId == -1)
//        {
//            Toast.makeText(context, "No radios checked", Toast.LENGTH_SHORT).show()
//        }
//        else
//        {
//            Toast.makeText(context, "radios checked", Toast.LENGTH_SHORT).show()
//        }

            //}

            // notifyItemChanged(position)

//
//        holder.itemView.setOnClickListener{
////            question.answer = options[position]
//             =holder.binding.mcqRG.checkedRadioButtonId
//            Toast.makeText(context, checkedID.toString(), Toast.LENGTH_SHORT).show()
//            Log.d("Radio", checkedID.toString())
////            notifyDataSetChanged()
//
//        }


//        Glide.with(holder.itemView.context).load(movie.imageUrl).into(holder.binding.imageview)



    }

    private fun new(holder: mcqViewholder, position: Int)
    {
        var rbGroup: RadioGroup = holder.binding.mcqRG
        holder.binding.progressBar.progress = current_pos
        holder.binding.tvProgress.text = "$current_pos" + "/" + holder.binding.progressBar.max
        current_pos++
        iterator++

        if (rbGroup.checkedRadioButtonId == -1) {

            Toast.makeText(context, "please select your answer", Toast.LENGTH_SHORT).show()
            holder.binding.mcqSubmit.isEnabled = false
            rbGroup.clearCheck()

        }
        else
            holder.binding.mcqSubmit.isEnabled = true



        if(current_pos <= az!!.size)
        {

            setMcqs(iterator, current_pos, az)


        }
        else{//Toast.makeText(context, "quiz khtm", Toast.LENGTH_SHORT).show()
            //fire_alert()

                if (haveTFS)
                {
//                    val intent = Intent(context,TruefalseActivity::class.java)
//                    intent.putExtra("filterd_trufalses", filterd_trufalses)
//                    context.startActivity(intent)
//                    (context as Activity).finish()

                    val intent = Intent(context, TruefalseActivity::class.java).apply {

                        // putExtra("filtered_topics", filterd_topics)
                        putExtra("filterd_trufalses", filterd_trufalses)
                        putExtra("scores", correct_answers)
                        putExtra("total_mcqs", az.size)
                    }
                    context.startActivity(intent)

                }
            else{
                    positive_results_alert(az)
            }

        }

        when {
            current_pos == az!!.size -> {
                holder.binding.mcqSubmit.text = "Finish"
             //   Toast.makeText(context, "quiz done", Toast.LENGTH_SHORT).show()

            }

    }
    }

    private fun fire_alert() {
        val dialogBuilder = AlertDialog.Builder(context)

        // set message of alert dialog
        dialogBuilder.setMessage("Results")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("return to chat", DialogInterface.OnClickListener {
                    dialog, id -> val intent = Intent(context,HomeActivity::class.java)
                   context.startActivity(intent)
            })
            // negative button text and action
//            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
//                    dialog, id -> dialog.cancel()
//            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Results")
        // show alert dialog
        alert.show()
    }

    fun positive_results_alert(az: ArrayList<Mcqss>)
    {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.positive_feedback,null)
        val Score = view.findViewById<TextView>(R.id.Results_score_tv)
        Score.text = "You got"+" "+ correct_answers.toString() +" "+ "out of" +" " + az.size
        val  button = view.findViewById<Button>(R.id.Results_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {
            val intent = Intent(context,HomeActivity::class.java)
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


    private fun next(holder: mcqViewholder, position: Int) {

        if (mSelectedOptionPosition == 0) {
            current_pos++
            iterator++

            when {
                current_pos <= az!!.size -> {
                   setMcqs(iterator, current_pos, az)

                }
                else -> {
                    holder.binding.mcqSubmit.text = "Finish"
                    notifyDataSetChanged()
                   // Toast.makeText(context, "quiz done", Toast.LENGTH_SHORT).show()


//                    Toast.makeText(
//                        this,
//                        "You have successfully completed the Quiz", Toast.LENGTH_SHORT
//                    ).show()
//                    val intent = Intent(this,MainActivity::class.java)
//                    startActivity(intent)
                }
            }
        }
        else if(current_pos == az!!.size) {
            //Toast.makeText(context, "quiz donesssss", Toast.LENGTH_SHORT).show()
        }
        else {
            //  val question = mQuestionList?.get(mCurrentPosition - 1)
//            if (question!!.correctOption != mSelectedOptionPosition) {
//              //  answerView(mSelectedOptionPosition, R.drawable.wrong_option_border_bg)
//            }
            //   answerView(question.correctOption, R.drawable.correct_option_border_bg)
            if (current_pos == az!!.size) {
                Log.d("finished", "numberbazi")
                // btn_submit.text = "Finish"

            } else {
                //btn_submit.text = "Go to next question"
            }
            mSelectedOptionPosition = 0
        }
    }

    override fun getItemCount(): Int {
            return mcqs.size
        }

      inner class mcqViewholder(val binding: McqItemBinding) : RecyclerView.ViewHolder(binding.root) {

        }

//    interface CallbackInterface {
//        fun passResultCallback(message: String)
//    }

}



