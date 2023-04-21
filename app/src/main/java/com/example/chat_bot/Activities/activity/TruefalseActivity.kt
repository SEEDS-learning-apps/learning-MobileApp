package com.example.chat_bot.Activities.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chat_bot.R
import com.example.chat_bot.data.Data
import com.example.chat_bot.data.Mcqss
import com.example.chat_bot.data.Topics
import com.example.chat_bot.databinding.ActivityTruefalseBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.ui.Tfadapter
import com.example.chat_bot.utils.SessionManager

class TruefalseActivity : AppCompatActivity() {

    private val TAG = "TFActivity"
    private val SPLASH_TIME: Long = 2000
    lateinit var session: SessionManager
    val handler = Handler(Looper.getMainLooper())
    lateinit var viewModel: SEEDSViewModel
    private lateinit var binding: ActivityTruefalseBinding
    private var correct_answers: Int = 0
    lateinit var q_mcqs: ArrayList <Mcqss>
     var TFlist: ArrayList<Data> = arrayListOf()
     var filterd_topiclist: ArrayList<Topics> = arrayListOf()
     var filterd_trufalses: ArrayList<Data> = arrayListOf()
    private var totale_mcq : Int = 0
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var topic_id: String
    lateinit var user_id: String
    val adapter = Tfadapter(this)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_truefalse)

        binding = ActivityTruefalseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(SEEDSViewModel::class.java)
        session = SessionManager(applicationContext)


            filterd_trufalses = intent.getSerializableExtra("filterd_trufalses") as ArrayList<Data>
            correct_answers = intent.getIntExtra("scores", correct_answers)
            totale_mcq = intent.getIntExtra("total_mcqs", totale_mcq)





        var country: String = ""

        country=  session.pref.getString("country", country).toString()
        Log.d("OnCreate", country)



        binding.tfRv.layoutManager
        binding.tfRv.adapter = adapter
        binding.loadingProgress.visibility = View.VISIBLE


        // val sharedPreferences: SharedPreferences = this.getSharedPreferences(Context.MODE_PRIVATE.toString())

        //val dev = sharedPreferences.getString("dev_id", "")

        //LocalBroadcastManager.getInstance(this).registerReceiver(mFlagReciever, IntentFilter("Answer_flag"))

//
//        viewModel.tfList.observe(this, Observer {
//            Log.d(TAG, "OnCreate: $it")
//            var datasize = it.size
//
//
//
//            var mil: MutableList<String> = arrayListOf()
//
//            // for (item in it)
//            // {
//            for (item in filterd_topiclist)
//            {
//                topic_id = item._id
//                user_id = item.userId
//
//                TFlist = item.filter { it.topicId == topic_id && it.userId == user_id } as ArrayList<trufalses>
//
//                TFlist = item.filter { it.topicId == topic_id && it.userId == user_id} as ArrayList<trufalses>
//
//            }


                adapter.setMcqList(filterd_trufalses, correct_answers, totale_mcq)


//
//            Log.d("MAA", TFlist.toString())
//            // }
//
//        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, "Error went", Toast.LENGTH_SHORT).show()
            binding.loadingProgress.visibility = View.GONE
        })

        handler.postDelayed({
            viewModel.getAllTF()

            binding.loadingProgress.visibility = View.GONE
        },SPLASH_TIME)

    }

    private fun fill_tf() {
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
                    it.data.filter { it.topicId == topic_id && it.userId == user_id } as ArrayList<Data>
                if (TFlist.isNotEmpty())
                {
                   adapter.setMcqList(TFlist,correct_answers,totale_mcq)
                }

            }


            //  adapter.setMcqList(mcqlist)

            Log.d("OnCreate", TFlist.toString())
            // }

        })
        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, "NO TF found", Toast.LENGTH_SHORT).show()

        })
        viewModel.getAllTF()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}