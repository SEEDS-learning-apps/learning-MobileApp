package com.example.chat_bot.Activities.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.data.Data
import com.example.chat_bot.data.Mcqss
import com.example.chat_bot.data.Topics
import com.example.chat_bot.databinding.ActivityMcqsBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.*
import com.example.chat_bot.ui.McqAdapter
import com.example.chat_bot.utils.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Mcqs_activity : AppCompatActivity() {

    private val TAG = "McqActivity"
    private val SPLASH_TIME: Long = 2000
    lateinit var session: SessionManager
    val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivityMcqsBinding
    lateinit var viewModel: SEEDSViewModel
    lateinit var mcqlist: ArrayList<Mcqss>
    var TFlist: ArrayList<Data> = arrayListOf()
    lateinit var filterd_topiclist: ArrayList<Topics>
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var topic_name: String
    lateinit var topic_id: String
    lateinit var user_id: String
    var haveTFS: Boolean = false
    val adapter = McqAdapter(this)


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mcqs)
        binding = ActivityMcqsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        session = SessionManager(applicationContext)

        filterd_topiclist = intent.getSerializableExtra("filtered_topics") as ArrayList<Topics>
        topic_name = intent.getSerializableExtra("selected_topic").toString()

        filterd_topiclist.size

        var country: String = ""

        country=  session.pref.getString("country", country).toString()
        Log.d("OnCreate", country)


        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(SEEDSViewModel::class.java)
        binding.mcqsRv.layoutManager
        binding.mcqsRv.adapter = adapter
        binding.loadingProgress.visibility = View.VISIBLE


        mcqlist = filterd_topiclist as ArrayList<Mcqss>

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, Thread.currentThread().name)
        }



        viewModel.mcqList.observe(this, Observer {
            Log.d(TAG, "OnCreate: $it")
            for (item in filterd_topiclist)
            {
                topic_id = item._id
                user_id = item.userId
                mcqlist = it.filter { it.topicId == topic_id  } as ArrayList<Mcqss>

            }
            Log.d("mcq", mcqlist.size.toString())


            mcqlist

            if (mcqlist.isEmpty())
            {
                startActivity(Intent(this,HomeActivity::class.java))
                finish()

            }
            else
            { adapter.setMcqList(mcqlist, haveTFS, TFlist)}

            Toast.makeText(applicationContext, "No mcqs matched", Toast.LENGTH_SHORT).show()

            Log.d("MAA", mcqlist.toString())

        })


        viewModel.tfList.observe(this, Observer {
            Log.d(TAG, "OnCreate: $it")

            for (item in filterd_topiclist) {
                topic_id = item._id
                user_id = item.userId

                TFlist =
                    it.data.filter { it.topicId == topic_id } as ArrayList<Data>
                if (TFlist.isNotEmpty())
                {
                    haveTFS = true
                }

            }

            Log.d("OnCreate", TFlist.toString())

        })
        viewModel.getAllTF()

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, "Error went", Toast.LENGTH_SHORT).show()
            binding.loadingProgress.visibility = View.GONE
        })

        handler.postDelayed({
            viewModel.getAllMcqs()

            binding.loadingProgress.visibility = View.GONE
        },SPLASH_TIME)

    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

}
