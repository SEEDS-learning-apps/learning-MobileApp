package com.example.chat_bot.Activities.DashboardActivities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.Relations.UserAndMaterials
import com.example.chat_bot.Room.Relations.UserAndMessage
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.databinding.ActivityDownloadQuizBinding
import com.example.chat_bot.ui.dwnQuizAdapter
import com.example.chat_bot.utils.SessionManager
import kotlinx.coroutines.launch

class downloadQuizActivity : AppCompatActivity(), dwnQuizAdapter.dwnQuizAdapterListener {

    lateinit var session: SessionManager
    lateinit var quizllist: List<UserAndMaterials>
    val adapter = dwnQuizAdapter(this)
    private lateinit var binding: ActivityDownloadQuizBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_quiz)
        adapter.setListener(this)
        binding = ActivityDownloadQuizBinding.inflate(layoutInflater)

        session = SessionManager(this)
        recyclerView()


        setContentView(binding.root)
        hideActionBar()

        val backbtn = findViewById<ImageView>(R.id.Backbutton_downloadmaterials)

        backbtn.setOnClickListener{
            onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
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


            val user: List<UserAndMessage> = dao.getMessagesAndUserwithUsername(username)
            user

            Log.d("dwnnn", list.size.toString())

            Log.d("listaaa", list.toString())
            if (list.isNotEmpty()) {
                manageViews(false) // Call the method without parentheses
                adapter.setdwnList(list)
                adapter.notifyDataSetChanged()
                adapter.notifyItemInserted(list.size)
            } else {
                manageViews(true) // Call the method without parentheses
            }

        }


    }




    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 2)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun manageViews(isEmpty: Boolean) {
        if (isEmpty) {
            binding.dwnQuizRv.visibility = View.GONE
            binding.noDownloads.visibility = View.VISIBLE
        } else {
            binding.dwnQuizRv.visibility = View.VISIBLE
            binding.noDownloads.visibility = View.GONE
        }
    }
}