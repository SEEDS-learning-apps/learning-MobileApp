package com.example.chat_bot.Activities.acivity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.Relations.UserAndMaterials
import com.example.chat_bot.Room.Relations.UserAndMessage
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.databinding.ActivityDownloadQuizBinding
import com.example.chat_bot.ui.dwnQuizAdapter
import com.example.chat_bot.utils.SessionManager
import kotlinx.coroutines.launch

class downloadQuizActivity : AppCompatActivity() {

    lateinit var session: SessionManager
    lateinit var quizllist: List<UserAndMaterials>
    val adapter = dwnQuizAdapter(this)

    private lateinit var binding: ActivityDownloadQuizBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadQuizBinding.inflate(layoutInflater)

        session = SessionManager(this)
        recyclerView()


        setContentView(binding.root)
        hideActionBar()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerView() {


        binding.dwnQuizRv.adapter = adapter
        binding.dwnQuizRv.layoutManager = LinearLayoutManager(this)

        val userDetails = session.getUserDetails()
        val username = userDetails.get("name").toString()

        var list: List<QuestItem> = listOf()



        val dao: SeedsDao = SeedsDatabase.getInstance(this).seedsDao
        lifecycleScope.launch{

         list =  dao.getMaterialsWithUsername(username)
            list

            val user: List<UserAndMessage> = dao.getMessagesAndUserwithUsername(username)
            user

            Log.d("dwnnn", list.size.toString())

            Log.d("listaaa", list.toString())
            if(list.isNotEmpty())
            {

                manage_views()
                adapter.setdwnList(list)
                adapter.notifyDataSetChanged()
                adapter.notifyItemInserted(list.size)
            }

                //Toast.makeText(this.coroutineContext, "No downloads available", Toast.LENGTH_SHORT).show()


        }

      // list = session.loadQuiz(this) as ArrayList<DowloadedQuiz>






        //  Log.v(TAG, "ReCYCLE")

        // }

    }

    private fun manage_views() {
        binding.dwnQuizRv.visibility = View.VISIBLE
        binding.noDownloads.visibility = View.GONE

    }


    private fun hideActionBar() {
        supportActionBar?.hide()
    }
}