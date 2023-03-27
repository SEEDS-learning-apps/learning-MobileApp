package com.example.chat_bot.data
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView

import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.databinding.MessageItemBinding
import com.example.chat_bot.utils.Constants.RCV_ID
import com.example.chat_bot.utils.Constants.SND_ID
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.launch


class msgAdapter(val jkt: Callbackinter, val context: Context) :  RecyclerView.Adapter<msgAdapter.msgViewholder>() {

    var msgList = mutableListOf<Message>()
    var filterd_topics: ArrayList<Topics> = arrayListOf()
    var size: Int = 0
    var iterator: Int = 0
    lateinit var topic1 :Topics
    lateinit var topic2: Topics
    lateinit var topic3: Topics
    lateinit var topic4: Topics
    lateinit var topic5: Topics
    lateinit var topic6: Topics
    lateinit var topic7: Topics
    lateinit var topic8: Topics


    // Contains all the views
    //private lateinit var binding:MessageItemBinding

    @SuppressLint("NotifyDataSetChanged")
    inner class msgViewholder(val binding: MessageItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            itemView.setOnClickListener{

                Log.d("dangle", itemView.toString())
                itemViewType
//                msgList.removeAt(absoluteAdapterPosition)
//                notifyDataSetChanged()
//                context.applicationContext
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): msgViewholder {

        return msgViewholder(MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: msgViewholder, position: Int) {
        val current_msg = msgList[position]

        Log.d("adapter", "messages start= ${msgList.size}")
        Log.d("adapter", "item type= ${getItemViewType(position)}")

        holder.binding.suggestionView.visibility= View.GONE
        when (current_msg.msgId){
            SND_ID -> {
                holder.binding.tvMessage.apply{
                    text = current_msg.message
                    visibility = View.VISIBLE

                }
                holder.binding.senderMsgTime.apply { text = current_msg.time }
                holder.binding.tvBotMessage?.visibility  = View.GONE
                holder.binding.suggestionView.visibility = View.GONE
                holder.binding.botMsgTime.visibility = View.GONE
            }

            RCV_ID -> {
                if (current_msg.has_suggestion)
                {

                    if (current_msg.buttons.isNotEmpty())
                    {
                        Log.d("poison", current_msg.buttons.size.toString())
                        Log.d("poison", "awara")
                    }
                    maintain_option(holder, position)

                }
                else
                {
                    holder.binding.tvBotMessage?.apply{
                        text = current_msg.message
                        visibility = View.VISIBLE
                    }
                    holder.binding.suggestionView.visibility = View.GONE
                }

                holder.binding.botMsgTime.apply { text = current_msg.time }
                holder.binding.senderMsgTime.visibility = View.GONE
                holder.binding.tvMessage.visibility = View.GONE
            }
        }
    }


    private fun maintain_option(holder: msgAdapter.msgViewholder, position: Int) {

        Log.d("adapterr", "topic size= ${filterd_topics.size}")

        if (filterd_topics.size>0)
        {
            filterd_topics.clear()
            Log.d("adapter", "topic size cleared= ${filterd_topics.size}")
        }
        val tops = msgList[position]

        Log.d("adapter", "called")
        Log.d("adapter", "msglist size= ${msgList.size}")
        Log.d("adapter", "topic size= ${filterd_topics.size}")



        holder.binding.tvBotMessage?.visibility   = View.GONE
        holder.binding.botMsgTime.visibility = View.GONE
        holder.binding.suggestionView.visibility = View.VISIBLE

        // holder.binding.suggestionView.setOnClickListener { jkt.passResultCallback("") }


        tops.buttons
        var msgBtn: List<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()
        msgBtn = tops.buttons

        Log.d("adapter", "Button are = ${tops.buttons.size}")

        for (item in tops.buttons)
        {
            filterd_topics.addAll(listOf(Topics(item.payload, item.title, "","", "","","","", "", "","")))
        }

        Log.d("adapter", "topic size after= ${filterd_topics.size}")
        var topicOne =""
        var topictwo =""
        var topicthree  =""
        var topicfour  =""
        var topicfive  =""
        var nxt  =""



        if (filterd_topics.size>= 8)
        {

            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]
            topic4 = filterd_topics[3]
            topic5 = filterd_topics[4]
            topic6 = filterd_topics[5]
            topic7 = filterd_topics[6]
            topic8 = filterd_topics[7]


            tops.buttons[0].title

            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            holder.binding.suggesstionBtn4.apply { text = tops.buttons[3].title }.toString()
            holder.binding.suggesstionBtn5.apply { text = tops.buttons[4].title }.toString()
            holder.binding.suggesstionBtn6.apply { text = tops.buttons[5].title }.toString()
            holder.binding.suggesstionBtnNXT.apply { text = tops.buttons[6].title }.toString()
            holder.binding.suggesstionBtnStop.apply { text = tops.buttons[7].title }.toString()


            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                Topics(topic2._id, topic2.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn3.setOnClickListener { jkt.passResultCallback(
                Topics(topic3._id, topic3.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn4.setOnClickListener { jkt.passResultCallback(
                Topics(topic4._id, topic4.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn5.setOnClickListener { jkt.passResultCallback(
                Topics(topic5._id, topic5.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn6.setOnClickListener { jkt.passResultCallback(
                Topics(topic6._id, topic6.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtnNXT.setOnClickListener { jkt.passResultCallback(
                Topics(topic7._id, topic7.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtnStop.setOnClickListener { jkt.passResultCallback(
                Topics(topic8._id, topic8.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

        }

        else if (filterd_topics.size == 7)
        {

            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]
            topic4 = filterd_topics[3]
            topic5 = filterd_topics[4]
            topic6 = filterd_topics[5]
            topic7 = filterd_topics[6]


            tops.buttons[0].title

            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            holder.binding.suggesstionBtn4.apply { text = tops.buttons[3].title }.toString()
            holder.binding.suggesstionBtn5.apply { text = tops.buttons[4].title }.toString()
            holder.binding.suggesstionBtn6.apply { text = tops.buttons[5].title }.toString()
            holder.binding.suggesstionBtnNXT.apply { text = tops.buttons[6].title }.toString()


            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "",""))
               removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                Topics(topic2._id, topic2.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn3.setOnClickListener { jkt.passResultCallback(
                Topics(topic3._id, topic3.topic,"",
                    "", "","","","", "", "",""))
               removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn4.setOnClickListener { jkt.passResultCallback(
                Topics(topic4._id, topic4.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn5.setOnClickListener { jkt.passResultCallback(
                Topics(topic5._id, topic5.topic,"",
                    "", "","","","", "", "",""))
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn6.setOnClickListener { jkt.passResultCallback(
                Topics(topic6._id, topic6.topic,"",
                    "", "","","","", "", "",""))
               removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtnNXT.setOnClickListener { jkt.passResultCallback(
                Topics(topic7._id, topic7.topic,"",
                    "", "","","","", "", "",""))
               removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtnStop.visibility = View.GONE

        }


        else if (filterd_topics.size == 6)
        {

            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]
            topic4 = filterd_topics[3]
            topic5 = filterd_topics[4]
            topic6 = filterd_topics[5]


            tops.buttons[0].title

            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            holder.binding.suggesstionBtn4.apply { text = tops.buttons[3].title }.toString()
            holder.binding.suggesstionBtn5.apply { text = tops.buttons[4].title }.toString()
            holder.binding.suggesstionBtn6.apply { text = tops.buttons[5].title }.toString()


            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "","") )
               removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                Topics(topic2._id, topic2.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn3.setOnClickListener { jkt.passResultCallback(
                Topics(topic3._id, topic3.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn4.setOnClickListener { jkt.passResultCallback(
                Topics(topic4._id, topic4.topic,"",
                    "", "","","","", "", "","")

            )
             removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn5.setOnClickListener { jkt.passResultCallback(
                Topics(topic5._id, topic5.topic,"",
                    "", "","","","", "", "","")

            )
               removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn6.setOnClickListener { jkt.passResultCallback(
                Topics(topic6._id, topic6.topic,"",
                    "", "","","","", "", "","")


            )
                removeButton(position)
            }

//            notifyItemRemoved(position)

            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE

        }
        else if (filterd_topics.size == 5)
        {
            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]
            topic4 = filterd_topics[3]
            topic5 = filterd_topics[4]


            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            holder.binding.suggesstionBtn4.apply { text = tops.buttons[3].title }.toString()
            holder.binding.suggesstionBtn5.apply { text = tops.buttons[4].title }.toString()



            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                Topics(topic2._id, topic2.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn3.setOnClickListener { jkt.passResultCallback(
                Topics(topic3._id, topic3.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
            }
            holder.binding.suggesstionBtn4.setOnClickListener { jkt.passResultCallback(
                Topics(topic4._id, topic4.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }

            holder.binding.suggesstionBtn5.setOnClickListener { jkt.passResultCallback(
                Topics(topic5._id, topic5.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
                }

            holder.binding.suggesstionBtn6.visibility = View.GONE
            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE
        }


        else if (filterd_topics.size == 4) {

            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]
            topic4 = filterd_topics[3]



            topicOne =
                holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            topictwo =
                holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            topicthree =
                holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            topicfour =
                holder.binding.suggesstionBtn4.apply { text = tops.buttons[3].title }.toString()

            holder.binding.suggesstionBtn5.visibility = View.GONE
            holder.binding.suggesstionBtn6.visibility = View.GONE
            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE


            holder.binding.suggesstionBtn1.setOnClickListener {
                jkt.passResultCallback(
                    Topics(
                        topic1._id, topic1.topic, "",
                        "", "", "", "", "", "", "", ""
                    )

                )
                removeButton(position)
//                notifyItemRemoved(position)

            }
            holder.binding.suggesstionBtn2.setOnClickListener {
                jkt.passResultCallback(
                    Topics(
                        topic2._id, topic2.topic, "",
                        "", "", "", "", "", "", "", ""
                    )

                )

                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn3.setOnClickListener {
                jkt.passResultCallback(
                    Topics(
                        topic3._id, topic3.topic, "",
                        "", "", "", "", "", "", "", ""
                    )

                )
               removeButton(position)
//                notifyItemRemoved(position)
            }
                holder.binding.suggesstionBtn4.setOnClickListener {
                    jkt.passResultCallback(
                        Topics(
                            topic4._id, topic4.topic, "",
                            "", "", "", "", "", "", "", ""
                        )

                    )

                removeButton(position)
//                    notifyItemRemoved(position)
                }

            }

        else if (filterd_topics.size == 3)
        {
            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]
            topic3 = filterd_topics[2]


            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.apply { text = tops.buttons[2].title }.toString()
            holder.binding.suggesstionBtn4.visibility = View.GONE
            holder.binding.suggesstionBtn5.visibility = View.GONE
            holder.binding.suggesstionBtn6.visibility = View.GONE
            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE

            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "","")

            )

                removeButton(position)
//                notifyItemRemoved(position)
            }
            holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                Topics(topic2._id, topic2.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
            }
            holder.binding.suggesstionBtn3.setOnClickListener { jkt.passResultCallback(
                Topics(topic3._id, topic3.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }


        }
        else  if (filterd_topics.size == 2)
        {
            Log.d("adapter", "filterd_topics $filterd_topics.size")


            topic1 = filterd_topics[0]
            topic2 = filterd_topics[1]



            holder.binding.suggesstionBtn1.apply { text = tops.buttons[0].title }.toString()
            holder.binding.suggesstionBtn2.apply { text = tops.buttons[1].title }.toString()
            holder.binding.suggesstionBtn3.visibility = View.GONE
            holder.binding.suggesstionBtn4.visibility = View.GONE
            holder.binding.suggesstionBtn5.visibility = View.GONE
            holder.binding.suggesstionBtn6.visibility = View.GONE
            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE




            try {
                holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                    Topics(topic1._id, topic1.topic,"",
                        "", "","","","", "", "","")


                )

                    removeButton(position)
//                    notifyItemRemoved(position)
                }
                holder.binding.suggesstionBtn2.setOnClickListener { jkt.passResultCallback(
                    Topics(topic2._id, topic2.topic,"",
                        "", "","","","", "", "","")

                )


                   removeButton(position)
//                    notifyItemRemoved(position)
                }
            }catch (e: Exception) {

                Toast.makeText(context, "msg not deleted", Toast.LENGTH_SHORT).show()
            }

        }

        else  if (filterd_topics.size == 1)
        {
            topic1 = filterd_topics[0]

            topicOne = holder.binding.suggesstionBtn1.apply { text = topic1.topic }.toString()
            holder.binding.suggesstionBtn2.visibility = View.GONE
            holder.binding.suggesstionBtn3.visibility = View.GONE
            holder.binding.suggesstionBtn4.visibility = View.GONE
            holder.binding.suggesstionBtn5.visibility = View.GONE
            holder.binding.suggesstionBtn6.visibility = View.GONE
            holder.binding.suggesstionBtnNXT.visibility = View.GONE
            holder.binding.suggesstionBtnStop.visibility = View.GONE


            holder.binding.suggesstionBtn1.setOnClickListener { jkt.passResultCallback(
                Topics(topic1._id, topic1.topic,"",
                    "", "","","","", "", "","")

            )
                removeButton(position)
//                notifyItemRemoved(position)
            }

        }


    }


    fun removeButton(position: Int) {
        msgList.removeAt(position)
        notifyItemRemoved(position)
//        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertMessage(message: Message) {
        this.msgList.add(message)
       notifyItemInserted(this.msgList.size)



    }
    fun publishSuggestion(filterd_topics: ArrayList<Topics>)
    {

        this.filterd_topics = filterd_topics
        size  = filterd_topics.size
       // notifyItemInserted(this.filterd_topics.size)
       // notifyDataSetChanged()
        Log.d("adapter", "Publish suggestions filter = ${filterd_topics.size}")

    }

    fun setMessages(msgList: MutableList<Message>) {
        this.msgList = msgList
    }

    override fun getItemCount(): Int {

        return msgList.size
    }


    interface Callbackinter {
        fun passResultCallback(message: Topics )
    }

}
