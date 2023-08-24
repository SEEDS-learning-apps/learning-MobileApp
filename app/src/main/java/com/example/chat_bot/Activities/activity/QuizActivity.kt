package com.example.chat_bot.Activities.activity


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.Activities.DashboardActivities.downloadQuizActivity
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.AllQuestion
import com.example.chat_bot.data.OpenEnded
import com.example.chat_bot.data.QuestItem
import com.example.chat_bot.data.Topics
import com.example.chat_bot.databinding.ActivityQuizBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.ui.quiz_adapter
import com.example.chat_bot.utils.SessionManager
import com.example.chat_bot.utils.Time
import kotlinx.coroutines.*


class QuizActivity : AppCompatActivity(), quiz_adapter.Callbackinter {

    lateinit var session: SessionManager
    private lateinit var binding: ActivityQuizBinding
    lateinit var viewModel: SEEDSViewModel
    private val KEY_RECYCLER_STATE = "recycler_state"
    var question: MutableList<AllQuestion> = arrayListOf()
    lateinit var filterd_topiclist: ArrayList<Topics>
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var topic_name: String
    lateinit var user_name: String
    lateinit var topic_id: String
    lateinit var ActivityType: String
    val adapter = quiz_adapter(this, this)
    private var mBundleRecyclerViewState: Bundle? = null
    lateinit var listState: Parcelable
    var downloadedQuiz: ArrayList<QuestItem> = arrayListOf()
    lateinit var questItem: QuestItem
    var que: ArrayList<QuestItem> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        session = SessionManager(this)
        binding.dwnQuiz.setOnClickListener {

            lifecycleScope.launch {  down()}
             }

        setContentView(binding.root)
        hideActionBar()

        topic_id = session.get_topicID()

        ////////////////////////////////////////////////////////////////////////////
        listState = binding.mcqsRv.getLayoutManager()?.onSaveInstanceState()!!

        ActivityType = intent.getStringExtra("OfflineActivity").toString()

        binding.mcqsRv.layoutManager
        binding.mcqsRv.adapter = adapter
        binding.loadingProgress.visibility = View.VISIBLE

        if (ActivityType == "OfflineActivity")
        {
            LoadOfflineActivities()
            binding.loadingProgress.visibility = View.GONE
        }
        else
        {
            GlobalScope.launch {
                delay(2000)
                withContext(Dispatchers.Main) {

                    filterd_topiclist =
                        intent.getSerializableExtra("filtered_topics") as ArrayList<Topics>
                    topic_name = intent.getSerializableExtra("selected_topic").toString()
                    question = intent.getSerializableExtra("Quiz") as ArrayList<AllQuestion>

                    question.distinctBy { i->i._id }
                    downloadedQuiz =
                        intent.getSerializableExtra("Whole com.example.chat_bot.Lists.Quest") as ArrayList<QuestItem>

                    adapter.setQuizList(question as ArrayList<AllQuestion>, topic_name)
                    binding.loadingProgress.visibility = View.GONE

                }
            }
        }


        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(
            SEEDSViewModel::class.java
        )

    }

    private fun LoadOfflineActivities() {
        questItem = intent.getSerializableExtra("offlineQuiz") as QuestItem


        if (questItem != null)
        {
            LoadMaterials(questItem)
        }

    }

    private fun LoadMaterials(questItem: QuestItem) {

        que.addAll(listOf(questItem))
        for (item in que)
        {
            topic_name = item.topic
            question.addAll(item.allQuestions)
            question.sortBy { i->i.sequence }

            item.allQuestions.apply {

                sortedBy { i->i.sequence }

                for (k in question)
                {

                    if (k.questionType == "mcqs")
                    {

                        k.q_type = 2
                    }
                    else if (k.questionType== "trueFalse")
                    {
                        k.q_type = 3

                    }
                    else if (k.questionType== "openEnded")
                    {

                        k.q_type = 5
                    }
                    else if (k.questionType== "introduction")
                    {
                        k.q_type = 1

                    }
                    else if (k.questionType== "matchPairs")
                    {
                        k.q_type = 4
                    }
                }

            }

        }

            adapter.setQuizList(question as ArrayList<AllQuestion>, topic_name)
            binding.loadingProgress.visibility = View.GONE
        }


    override fun onPause() {
        super.onPause()
        mBundleRecyclerViewState = Bundle()
        listState = binding.mcqsRv.getLayoutManager()?.onSaveInstanceState()!!
        mBundleRecyclerViewState!!.putParcelable(KEY_RECYCLER_STATE, listState)
    }

    override fun onResume() {
        super.onResume()
        if (listState != null) {
            binding.mcqsRv.getLayoutManager()?.onRestoreInstanceState(listState);
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        mBundleRecyclerViewState = Bundle()
        listState = binding.mcqsRv.getLayoutManager()?.onSaveInstanceState()!!
        mBundleRecyclerViewState!!.putParcelable(KEY_RECYCLER_STATE, listState)
        outState.putCharSequence("na", listState.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState[KEY_RECYCLER_STATE]
    }

    private suspend fun down()
    {

        var topicID: String = ""
        var questItem: List<QuestItem> = listOf()

        downloadedQuiz.forEach { topicID = it._id }

        var dao: SeedsDao = SeedsDatabase.getInstance(this@QuizActivity).seedsDao

          questItem = dao.checkMaterialsWithID(topicID)
        Log.d("alreadyyy", questItem.size.toString())
        questItem.size
        if (questItem.isNotEmpty())
        {
            Toast.makeText(applicationContext, "This material is already in the download list", Toast.LENGTH_SHORT).show()
        }

        else
        {
            var userDetails = session.getUserDetails()
            val timeStamp = Time.timeStamp()
            user_name = userDetails["name"].toString()

            downloadedQuiz.forEach { it.username = user_name
                it.time = timeStamp

                var dao: SeedsDao = SeedsDatabase.getInstance(this).seedsDao

                lifecycleScope.launch { dao.insertMaterial(downloadedQuiz) }

            }
            Toast.makeText(applicationContext, "Quiz downloading", Toast.LENGTH_SHORT).show()

            Log.d("downloadedQuiz", downloadedQuiz.size.toString())
            if (downloadedQuiz.size>0)
            {
                Toast.makeText(applicationContext, "Quiz downloaded!!", Toast.LENGTH_SHORT).show()
                showDownload()
            }
        }

    }

    private fun showDownload() {
        val intent = Intent(this, downloadQuizActivity::class.java).apply {

            finish()
        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("FRAGMENT_TO_SHOW", 0)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)

    }

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun submitAnswerCallback(openEnded: OpenEnded) {

        Log.d("openzz", openEnded.toString())

        viewModel.op_response.observe(this) {

            if (it.isSuccessful)
            {
                Toast.makeText(this, "answerSubmitted", Toast.LENGTH_SHORT).show()
            }
            else
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()

        }
        viewModel.submitOpenEnded(openEnded)
    }

    override fun quizDonez() {

    }


}