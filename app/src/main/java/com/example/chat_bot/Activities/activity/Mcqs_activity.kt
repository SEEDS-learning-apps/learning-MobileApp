package com.example.chat_bot.Activities.activity

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
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
    lateinit var q_mcqs: ArrayList <Mcqss>
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


        // val sharedPreferences: SharedPreferences = this.getSharedPreferences(Context.MODE_PRIVATE.toString())

        //val dev = sharedPreferences.getString("dev_id", "")

        //LocalBroadcastManager.getInstance(this).registerReceiver(mFlagReciever, IntentFilter("Answer_flag"))

        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, Thread.currentThread().name)
        }



        viewModel.mcqList.observe(this, Observer {
            Log.d(TAG, "OnCreate: $it")
            var datasize = it.size
          //  var mil: MutableList<String> = arrayListOf()

            // for (item in it)
            // {
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



            // else
            Toast.makeText(applicationContext, "No mcqs matched", Toast.LENGTH_SHORT).show()
//            val intent = Intent(applicationContext, HomeActivity::class.java).apply {
//
//                // putExtra("filtered_topics", filterd_topics)
//
//            }
//            startActivity(intent)




            Log.d("MAA", mcqlist.toString())
            // }

        })


        viewModel.tfList.observe(this, Observer {
            Log.d(TAG, "OnCreate: $it")
//            var datasize = it.
            var mil: MutableList<String> = arrayListOf()



            // for (item in it)
            // {
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


            //  adapter.setMcqList(mcqlist)

            Log.d("OnCreate", TFlist.toString())
            // }

        })
        viewModel.getAllTF()
     //   viewModel.getAllMcqs()




        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, "Error went", Toast.LENGTH_SHORT).show()
            binding.loadingProgress.visibility = View.GONE
        })

        handler.postDelayed({
            viewModel.getAllMcqs()

            binding.loadingProgress.visibility = View.GONE
        },SPLASH_TIME)


//        binding.btnMcqNext.setOnClickListener{
//
//        }


        // onMcqclick()



        // Get the Intent that started this activity and extract the string
        val message = intent.getStringExtra(EXTRA_MESSAGE)

//        RetrofitInstance.api.getMcqs().enqueue(object : Callback<McqsListss>{
//            override fun onResponse(call: Call<McqsListss>, response: Response<McqsListss>) {
//                if (response.body() != null)
//                {
//                    val randomMcq : Mcqss = response.body()!![0]
//                    Log.d("Test", "Mcq id ${randomMcq._id}$")
//                    Toast.makeText(this@Mcqs_activity, "Loading quiz", Toast.LENGTH_SHORT).show()
//                }
//                else{ return }
//            }
//
//            override fun onFailure(call: Call<McqsListss>, t: Throwable) {
//                Log.d("Fail", t.message.toString())
//                Toast.makeText(this@Mcqs_activity, "Something went wrong", Toast.LENGTH_SHORT).show()
//            }
//
//        })


    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }


//    private fun onMcqclick() {
//        adapter.onItemClick = { Mcqss ->
//            Toast.makeText(this, Mcqss.answer, Toast.LENGTH_SHORT).show()
//        }
//    }
}
