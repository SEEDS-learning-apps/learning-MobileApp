package com.example.chat_bot.Activities.HomePage

import Quest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.activity.QuizActivity
import com.example.chat_bot.R
import com.example.chat_bot.Rasa.Networkings.api_Rasa
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.Subjects
import com.example.chat_bot.data.Topics
import com.example.chat_bot.data.tryy.AllQuestion
import com.example.chat_bot.databinding.FragmentChatzfragBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.ui.ChatAdapter
import com.example.chat_bot.utils.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class Chatzfrag : Fragment() {

    private lateinit var binding: FragmentChatzfragBinding
    private lateinit var messageList: ArrayList<UserMessage>
    private lateinit var btnList: ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button>
    private lateinit var adapter: ChatAdapter
    private lateinit var editText: EditText
    private lateinit var sendBtn: Button
    private lateinit var button_view: RecyclerView
    private lateinit var msgRV: RecyclerView

    lateinit var viewModel: SEEDSViewModel
    private val retrofitService = SEEDSApi.getInstance()

    private val url  = "https://ig1mceza29.execute-api.eu-central-1.amazonaws.com/" // ⚠️MUST END WITH "/"

    private val USER = "M-" + UUID.randomUUID().toString()
    private val BOT_TXT = "0"
    private val BOT_IMG = "1"
    private val BOT_BUT = "2"

    private var db: DB? = null
    var islearningstarted: Boolean= false
    var subjects: MutableList<Subjects> = arrayListOf()
    var quizez: MutableList<Quest> = arrayListOf()
    var question: MutableList<AllQuestion> = arrayListOf()
    var filterd_subjects: MutableList<Subjects> = arrayListOf()
    var quiz: ArrayList<AllQuestion> = arrayListOf()
    var filterd_topics: ArrayList<Topics> = arrayListOf()
    var b: ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()
    var topics: MutableList<Topics> = arrayListOf()
    private var isSubjectfetched: Boolean = false
    var istopicfetched: Boolean = false
    var isMaterialReady: Boolean = false
    private lateinit var checkConnection: connectionManager


    lateinit  var APP_MODE: String
    lateinit var topic_name: String
    lateinit var ageGroup: String
    lateinit var grades: String

    lateinit var topicLang: String
    var count: Int = 0

    lateinit var session: SessionManager

    lateinit var topic_id: String
    lateinit var user_id: String

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var nest: LinearLayout
    lateinit var smoothScroller: LinearSmoothScroller



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        binding = FragmentChatzfragBinding.inflate (layoutInflater,container,false)

        messageList = ArrayList<UserMessage>()
        btnList = ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button>()
        adapter = ChatAdapter(requireContext(), messageList)
        //  adapter.setHasStableIds(true)
        binding.messageList.adapter = adapter
//       nest= binding.lala

        // nest = binding.nest

        msgRV = binding.messageList
        msgRV = binding.buttonList


        db = this.context?.let { DB(it) }
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(
            SEEDSViewModel::class.java)

        linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.messageList.layoutManager = linearLayoutManager

        smoothScroller = object : LinearSmoothScroller(activity) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_END
            }
        }



        binding.btnSend.setOnClickListener {

            GlobalScope.launch {
                delay(200)
                withContext(Dispatchers.Main) {
                    val msg = binding.etMessage .text.toString().trim()
                    //  smoothScroller.setTargetPosition(adapter.itemCount -1)
//            msgRV.getLayoutManager()?.startSmoothScroll(smoothScroller)

                    if (msg != "") {
                        sendMessage(msg)
                        // getQuiz("62fe1bb09c4afe7b1a0bedfd")
                        binding.etMessage.setText("")
                        //     hideKeyboard()
                    } else {
                        Toast.makeText(requireContext(), "Please enter a message.", Toast.LENGTH_SHORT).show()
                    }

                    //  msgRV.scrollToPosition(messageList.size-1)

                }
            }



        }







            sendMessage(message = "hello",  display = false)







        // sendMessage("i wannt to learn", "learn", true)

        // Inflate the layoult for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val totalSCore =  requireActivity().intent.getStringExtra("Total score")
        val obtained =  requireActivity().intent.getStringExtra("Obtained")

        if (totalSCore!= null && obtained != null )
        {
            sendMessage("/activity_done{\"score1\": $obtained , \"score2\": $totalSCore }", display = false)
        }


        Log.d("scoring", totalSCore.toString())
        Log.d("scoring", obtained.toString())


    }

    fun sendMessage(message: String, alternative: String = "", display: Boolean = true) {
        val displayedMessage  = if (alternative.isNullOrEmpty()) message else alternative


        var userMessage       = UserMessage(USER, message) // The message that will be sent to Rasa (payload in case of buttons)
        var userDisplayed     = UserMessage(USER, displayedMessage) // The message that will be displayed on screen (title in case of buttons)

        if (display) {
            messageList.add(userDisplayed)
            // nest.scrollTo()
            linearLayoutManager.scrollToPosition(adapter.itemCount-1)

            // msgRV.scrollToPosition(adapter.itemCount-1)
           // adapter.notifyItemInserted(messageList.size)
            adapter.notifyDataSetChanged()
        }

        //val date = Date(System.currentTimeMillis())


        val okHttpClient = OkHttpClient()
//            .connectTimeout(1, TimeUnit.MINUTES)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .writeTimeout(15, TimeUnit.SECONDS)
//            .build()
//        val retrofit = Retrofit.Builder()
//            .baseUrl(url)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create()).build()





        val retrofit = Retrofit.Builder().baseUrl(url).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
        val messageSender = retrofit.create(api_Rasa::class.java)
        val response = messageSender.sendMessage(userMessage)

        response.enqueue(object: Callback<ArrayList<BotResponse>> {
            override fun onResponse(call: Call<ArrayList<BotResponse>>, response: Response<ArrayList<BotResponse>>) {
                if (response.body() != null && response.body()!!.size != 0) {
                    for (i in 0 until response.body()!!.size) {
                        val message = response.body()!![i]

                        try {
                            if (message.text.isNotEmpty()) {
                                messageList.add(UserMessage(BOT_TXT, message.text))
                                linearLayoutManager.scrollToPosition(messageList.size-1)



                            } else if (message.image.isNotEmpty()) {
                                messageList.add(UserMessage(BOT_IMG, message.image))
                                linearLayoutManager.scrollToPosition(messageList.size-1)

                            }
                        } catch (e: Exception) { }

                        try {
                            if (message.buttons != null) {
                                val buttonRecyclerView = ButtonRecyclerView(requireContext(), message.buttons)
                                val layoutManager = LinearLayoutManager(requireContext())



                                button_view = binding.buttonList
                                //button_view = findViewById<RecyclerView>(R.id.message_list)
                                button_view.adapter = buttonRecyclerView

//                                msgRV.scrollToPosition(messageList.size-1)
                                button_view.layoutManager = layoutManager
                                layoutManager.orientation = LinearLayoutManager.HORIZONTAL

                                Toast.makeText(requireContext(), adapter.itemCount.toString(), Toast.LENGTH_SHORT).show()


                                layoutManager.scrollToPosition(btnList.size-1)


                                /*for (j in 0 until message.buttons.size) {
                                    messageList.add(Message(BOT_TXT, message.buttons[j].title))*/
                            }
                        } catch (e: Exception) { }
                      //  adapter.notifyItemInserted(btnList.size)

                        adapter.notifyDataSetChanged()
                    }
                } else {
                    val message = "Sorry, something went wrong:\nReceived empty response."
                    messageList.add(UserMessage(BOT_TXT, message))
                    linearLayoutManager.scrollToPosition(adapter.itemCount-1)

                  //  adapter.notifyItemInserted(messageList.size)
                     adapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
                t.printStackTrace()
                val message = "Sorry, something went wrong:\n" + t.message
                messageList.add(UserMessage(BOT_TXT, message))
                linearLayoutManager.scrollToPosition(adapter.itemCount-1)

               // adapter.notifyItemInserted(messageList.size)
                adapter.notifyDataSetChanged()
            }
        })

        messageList
        Log.d("messageList", messageList.size.toString())
    }


//    fun sendMessage(message: String, alternative: String = "", display: Boolean = true) {
//        val displayedMessage  = if (alternative.isNullOrEmpty()) message else alternative
//        var userMessage       = UserMessage(USER, message) // The message that will be sent to Rasa (payload in case of buttons)
//        var userDisplayed     = UserMessage(USER, displayedMessage) // The message that will be displayed on screen (title in case of buttons)
//        binding.typingStatus.visibility = View.VISIBLE
//        binding.typingStatus.playAnimation()
//
//        GlobalScope.launch {
//            //response delay
//            delay(2000)
//            withContext(Dispatchers.Main) {
//
//                if (display) {
//                    messageList.add(userDisplayed)
//
//                    adapter.notifyItemInserted(messageList.size)
//
//
//                }
//
//                //val date = Date(System.currentTimeMillis())
//
//
//                val okHttpClient = OkHttpClient.Builder()
//                    .connectTimeout(1, TimeUnit.MINUTES)
//                    .readTimeout(30, TimeUnit.SECONDS)
//                    .writeTimeout(15, TimeUnit.SECONDS)
//                    .build()
//                val retrofit = Retrofit.Builder()
//                    .baseUrl(url)
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create()).build()
//
//
//                //val retrofit = Retrofit.Builder().baseUrl(url).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
//                val messageSender = retrofit.create(api_Rasa::class.java)
//                val response = messageSender.sendMessage(userMessage)
//
//                response.enqueue(object : Callback<ArrayList<BotResponse>> {
//                    override fun onResponse(
//                        call: Call<ArrayList<BotResponse>>,
//                        response: Response<ArrayList<BotResponse>>
//                    ) {
//                        if (response.body() != null && response.body()!!.size != 0) {
//                            for (i in 0 until response.body()!!.size) {
//                                val message = response.body()!![i]
//
//                                try {
//                                    if (message.text.isNotEmpty()) {
//                                        messageList.add(UserMessage(BOT_TXT, message.text))
//
//                                        stopAnimation()
//
//
//                                    } else if (message.image.isNotEmpty()) {
//                                        messageList.add(UserMessage(BOT_IMG, message.image))
//                                        stopAnimation()
//
//                                    }
//                                } catch (e: Exception) {
//                                    adapter.notifyDataSetChanged()
//                                }
//
//                                try {
//                                    if (message.buttons != null) {
//                                        val buttonRecyclerView =
//                                            ButtonRecyclerView(requireContext(), message.buttons)
//                                        val layoutManager = GridLayoutManager(requireContext(), 2)
//
//                                        button_view =
//                                            view!!.findViewById<RecyclerView>(R.id.button_list)
//                                        //button_view = findViewById<RecyclerView>(R.id.message_list)
//                                        button_view.adapter = buttonRecyclerView
//
//                                       // layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//                                        button_view.layoutManager = layoutManager
//                                        /*for (j in 0 until message.buttons.size) {
//                                    messageList.add(Message(BOT_TXT, message.buttons[j].title))*/
//                                    }
//                                } catch (e: Exception) {
//                                }
//
//                                adapter.notifyDataSetChanged()
//
//
//                            }
//                        } else {
//                            val message = "Sorry, something went wrong:\nReceived empty response."
//                            messageList.add(UserMessage(BOT_TXT, message))
//                            adapter.notifyDataSetChanged()
//                          //  adapter.notifyItemInserted(messageList.size)
//                            stopAnimation()
//
//                            msgRV.smoothScrollToPosition(messageList.size -1)
//                            val t = adapter.itemCount
//                            Toast.makeText(requireContext(), t.toString(), Toast.LENGTH_SHORT).show()
//
//                            sendVanillaMessage(message)
//
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
//                        t.printStackTrace()
//                        val message = "Sorry, something went wrong:\n" + t.message
//                        messageList.add(UserMessage(BOT_TXT, message))
//                        adapter.notifyItemInserted(messageList.size)
//                        stopAnimation()
//
//                    }
//                })
//
//            }
//
//        }
//    }

    private fun stopAnimation() {
        binding.typingStatus.cancelAnimation()
        binding.typingStatus.visibility = View.GONE
        //Scrolls us to the position of the latest message
        msgRV.scrollToPosition(messageList.size - 1)
    }

    private fun hideKeyboard() {
        this.view?.findFocus()?.let { view ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun sendVanillaMessage(message: String, alternative: String = "", display: Boolean = true) {
        val displayedMessage  = if (alternative.isNullOrEmpty()) message else alternative
        var userMessage       = UserMessage(USER, message) // The message that will be sent to Rasa (payload in case of buttons)
        var userDisplayed     = UserMessage(USER, displayedMessage) // The message that will be displayed on screen (title in case of buttons)

        if (display) {
            messageList.add(userDisplayed)
            adapter.notifyItemInserted(messageList.size)
            stopAnimation()
        }


        try {
            if (message.isNotEmpty()) {
                messageList.add(UserMessage(Constants.SND_ID, message))
                adapter.notifyItemInserted(messageList.size)


                botResponse(message, false)
            }
        } catch (e: Exception) { }


    }

    private fun botResponse(message: String, _yo:Boolean) {
        val timeStamp = Time.timeStamp()
        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()

        GlobalScope.launch {
            //response delay
            delay(2000)
            withContext(Dispatchers.Main) {

                if (_yo)
                {
                    //Gets the response
                    val response = Bot_replies.basicResponses(message, false)
                    messageList.add(UserMessage(Constants.RCV_ID, response))

                    //db!!.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp, false, ""))
                    //Inserts our message into the adapter


                    delay(2000)
                    stopAnimation()

                }
                else {
                    var ans: Boolean
                    //Gets the response
                    val res: Any = Bot_replies.basicResponses(message, false)
                    var response = Bot_replies.basicResponses(message,false)
                    ans = res.toString().contains("12")
                    if ( ans)
                    {
                        response = res.toString().replace("12", "")

                        islearningstarted=false
                        process_request(response)
                    }


                    messageList.add(UserMessage( response as String, Constants.RCV_ID))
                    stopAnimation()

                    when (response) {
                        Constants.SEEDS -> {



                        }                            }


                    if (response.contains("want to study") || response.contains("want to learn")
                        || response.contains("study") || response.contains("möchte lernen")
                        || response.contains("lernen wollen")
                        || response.contains("kennenlernen möchten") || response.contains("learn")) {

                    }

                }
            }
        }
    }

    private fun process_request(response: String) {

        offersubjects()
//        val intent = Intent (getActivity(), Mcqs_activity::class.java)
//        getActivity()?.startActivity(intent)
//        (context as Activity).finish()

    }
    private  fun offersubjects() {

        // botResponse("please_publish_sugesstion mry tha", false)

        //Todo offer subjects and topics
        //customMsg("which subject you want to learn")
        fetch_subjects()

    }

    private  fun fetch_subjects() {

        viewModel.subjectList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            Log.d(ContentValues.TAG, "OnCreate: $it")

            var datasize = it.size
            //   var sub_list: MutableList<Subjects> = arrayListOf()
            // subjects.addAll(it)
            // sub_list.addAll(it)
            subjects.addAll(it)
            subjects.size

            if (it.size<1)
            {
                // customMsg("Subject List is empty (fetch_subjects)", false, msgBtn)
                !istopicfetched
                !isMaterialReady
                !isSubjectfetched
                return@Observer
            }
            else
                isSubjectfetched = true


        } )

        viewModel.errorMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            Toast.makeText(this.requireContext(), "Error fetching subjects", Toast.LENGTH_SHORT).show()
            Log.d(ContentValues.TAG, "OnCreate: $it")

            isSubjectfetched = false
            istopicfetched = false

//            if (language== "en")
//            {
//                customMsg("I am facing problems at the moment", false, msgBtn)
//            }
//            if (language== "de")
//            {
//                customMsg("Ich habe derzeit Probleme", false, msgBtn)
//            }
//            if (language== "es")
//            {
//                customMsg("Actualmente tengo problemas", false, msgBtn)
//            }






            //binding.loadingProgress.visibility = View.GONE
        })

        viewModel.getAllSubjects()

    }
    private fun suggest_topic(filterd_topicss: ArrayList<Topics>) {
        val timeStamp = Time.timeStamp()
        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()


        //customMsg("you have following option")

        GlobalScope.launch {
            //response delay
            delay(2000)
            withContext(Dispatchers.Main) {

                if (filterd_topicss.isEmpty())
                {
                    //   customMsg("This topic is not found", false, msgBtn)
                    !istopicfetched
                    !isMaterialReady
                    !isSubjectfetched
                }
                else{
                    for (item in filterd_topicss)
                    {
                        topic_name = item.topic
                        ageGroup = item.ageGroup
                        topicLang = item.language
                        grades = item.grade

                        //customMsg(topic_name)

                        Log.d("taap", filterd_topicss.toString())
                    }

                    //    adapter.publishSuggestion(filterd_topicss)
                    // adapter.notifyDataSetChanged()



                    // customMsg("Hope you like to study this!!")

                    botResponse("please_publish_sugesstion", true)
                }



                //Gets the response
//                val response = Bot_replies.basicResponses("i want to learn bio", true)
//
//                adapter.insertMessage(Message(response.toString(), Constants.RCV_ID, timeStamp, true))
//                binding.typingStatus.cancelAnimation()
//                binding.typingStatus.visibility = View.GONE
//                //Scrolls us to the position of the latest message
//                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private  fun initiate_subject_filtration(mk: String) {

        // filterd_subjects = az.find { az -> az.subject==  mk  } as ArrayList<Subjects>


        // val fnd: MutableList<Subjects> = subjects.filter { it.subject == mk } as MutableList<Subjects>


//        var subz: ArrayList<Subjects>
//        var ans: Boolean = false
//
//       for (i in subjects)
//       {
//          subz = i.subject.trim().lowercase() as ArrayList<Subjects>
//
////           ans=  subz.toString().contains(mk.trim().lowercase())
////           if (ans == true)
////           {
////               Toast.makeText(context, "LUB ju", Toast.LENGTH_SHORT).show()
////               return
////           }
////           else
////           { Toast.makeText(context, "abhi so jatay hain", Toast.LENGTH_SHORT).show()}
//       }


        var found: Subjects? =  subjects.find { i -> i.subject.trim().lowercase() == mk.trim().lowercase() }
        if (found != null) {
            // Toast.makeText(context, "we got you" + " "+found.subject, Toast.LENGTH_SHORT).show()
            filterd_subjects.addAll(listOf(found))


            //customMsg("Which topic is intresting for you?")
            fetch_topics(filterd_subjects, mk)

        }

//        else{//Toast.makeText(context, "oops", Toast.LENGTH_SHORT).show()
//            isSubjectfetched = true
//            istopicfetched = false
//            isMaterialReady = false
//
//            if (language== "en")
//            {
//                customMsg("Sorry this one is not available", false, msgBtn)
//
//
//            }
//            if (language== "de")
//            {
//                customMsg("Dieses Thema ist leider nicht verfügbar", false, msgBtn)
//            }
//            if (language== "es")
//            {
//                customMsg("Lo sentimos, este tema no está disponible", false, msgBtn)
//            }
//            customMsg("Try looking for some other subjects", false, msgBtn)
////            customMsg("if you want me to look for another subject, Say yes!!")
////            lookmore = true
//        }


//        fnd.contains(mk)
//
//
//            if (fnd.contains(mk))
//            { Toast.makeText(context, "we got you" +mk, Toast.LENGTH_SHORT).show()}






//        if (filterd_subjects)
//        var found: Subjects? =  az.find { az -> az.subject == mk }
//        if (found != null) {
////            if (found.subject == sub)
////            {
////                Toast.makeText(context, "we got you", Toast.LENGTH_SHORT).show()
////
////                sendMessage()
////                fetch_topics()
////
////            }
//        }
//
//        else{ Toast.makeText(context, "no match found", Toast.LENGTH_SHORT).show()}
//
//            isSubjectfetched
//            istopicfetched
//
//        }
    }
    private  fun fetch_topics(filterd_subjects: MutableList<Subjects>, mk: String) {

        //topics from database
        viewModel.topicListss.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "OnCreate: $it")
            var datasize = it.size
            istopicfetched = true

            if (it.size<1)
            {
                //   customMsg("No topic found with $mk subject", false, msgBtn)
                !isSubjectfetched
                !isMaterialReady
                !istopicfetched
                return@observe
            }
            else

                for (item in filterd_subjects) {
                    filterd_topics = it.filter { it.subId == item._id } as ArrayList<Topics>
                    filterd_topics
                    suggest_topic(filterd_topics)
                }




            Log.d("filter_topics", filterd_topics.toString())
        }
        viewModel.getAllTopics()
        viewModel.errorMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            //Toast.makeText(this.requireContext(), "Error fetching topics", Toast.LENGTH_SHORT).show()
            istopicfetched = false
            isSubjectfetched = false
            isMaterialReady = false

//            if (language== "en")
//            {
//                customMsg("I am facing problems at the moment", false, msgBtn)
//            }
//            if (language== "de")
//            {
//                customMsg("Ich habe derzeit Probleme", false, msgBtn)
//            }
//            if (language== "es")
//            {
//                customMsg("Actualmente tengo problemas", false, msgBtn)
//            }
        })
    }



    fun loadNext(input: String): Boolean {
        val regex = Regex(pattern = "/next_option\\{\"subj\":\"None\"\\}", options = setOf(RegexOption.IGNORE_CASE))
        return regex.matches(input)
    }

    fun chekz(input: String): Boolean {
        val regex = Regex(pattern = "/inform_new\\{\\\\\"topic\\\\\":\\\\\"(?:[^\"]|\"\")*\"\\}", options = setOf(RegexOption.IGNORE_CASE))
        val ans: Boolean = regex.matches(input)
        return ans
    }

//    fun getTopicid(topicId: String) {
//        //val regex = Regex(pattern = "/inform_new\\{\\\\\"topic\\\\\":\\\\\"(([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?([a-zA-Z]+([+-]?(?=\\.\\d|\\d)(?:\\d+)?(?:\\.?\\d*))(?:[eE]([+-]?\\d+))?)+)\\\\\"\\}")
//        val regex = Regex(pattern = "/inform_new\\{\\\\\"topic\\\\\":\\\\\"([0-9]+([a-zA-Z]+[0-9]+)+)\\\\\"\\}\n")
//
//        val patterns: Pattern = regex.toPattern()
//
//        patterns.matcher()
//    }


    private fun extractVideoId(ytUrl: String?): String? {
        var videoId: String? = null
        val regex = "/([0-9]+([a-zA-Z]+[0-9]+)+)[a-zA-Z]+/"
        // "^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be|youtube-nocookie.com))(/(?:[\\w\\-]+\\?v=|feature=|watch\\?|e/|embed/|v/)?)([\\w\\-]+)(\\S+)?\$"
        val pattern: Pattern = Pattern.compile(
            regex ,
            Pattern.CASE_INSENSITIVE
        )
        val matcher: Matcher = pattern.matcher(ytUrl)
        if (matcher.matches()) {
            videoId = matcher.toString()

        }
        Toast.makeText(requireContext(), videoId.toString(), Toast.LENGTH_SHORT).show()
        return videoId
    }

    fun getTopicid(topicId: String?): String? {

        val pattern =
            "\"[^\"]*\"\\}"



        val compiledPattern = Pattern.compile(
            pattern,
            Pattern.CASE_INSENSITIVE
        )
        val matcher = compiledPattern.matcher(topicId)

        return if (matcher.find()) {
            matcher.group()
        } else null
    }


    fun getQuiz(id: String) {

        Toast.makeText(requireContext(), "Topic id = $id", Toast.LENGTH_SHORT).show()
        viewModel.quizList.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "OnCreate: $it")



            quizez.addAll(listOf(it))

            for (item in quizez) {
                for (i in item) {
                    question.addAll((i.allQuestions))
                    question.sortBy { i -> i.sequence }

                    for (k in question) {
                        if (k.questionType == "mcqs") {

                            k.q_type = 2
                        } else if (k.questionType == "trueFalse") {
                            k.q_type = 3

                        } else if (k.questionType == "openEnded") {

                            k.q_type = 5
                        } else if (k.questionType == "introduction") {
                            k.q_type = 1

                        } else if (k.questionType == "matchPairs") {
                            k.q_type = 4
                        }
                    }

                }
            }


            quiz = question as ArrayList<AllQuestion>
            Log.d("quizez", quiz.size.toString())
            //  Toast.makeText(this.requireContext(), quizez.toString(), Toast.LENGTH_SHORT).show()

        }

        viewModel.getQuiz(id)

        load_material(quiz)




        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(this.requireContext(), "No quiz available", Toast.LENGTH_SHORT).show()
        }


    }

    private fun load_material(quiz: ArrayList<AllQuestion>) {


        // checkmcqs()

        GlobalScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {


                if (this@Chatzfrag.quiz.isEmpty())
                {
                    Toast.makeText(context, "no quiz available ", Toast.LENGTH_SHORT).show()
                }

                else
                {

//                        if (language== "en")
//                        {
//                            customMsg("Best of luck for your quiz", false, msgBtn)
//                        }
//                        else if(language== "de")
//                        {
//                            customMsg("BViel Glück für deinen Test.", false, msgBtn)
//                        }
//                        else if (language== "es")
//                        {
//                            customMsg("La mejor de las suertes para tu prueba.", false, msgBtn)
//                        }

                    val intent = Intent(context, QuizActivity::class.java).apply {

                        Toast.makeText(context as Activity, "Loading quiz", Toast.LENGTH_SHORT).show()

                        // putExtra("filtered_topics", filterd_topics)
                        putExtra("filtered_topics", filterd_topics)
                        putExtra("selected_topic", "msg")
                        putExtra("Quiz", this@Chatzfrag.quiz)
                        (context as Activity).finish()
                    }
                    startActivity(intent)
                    hideKeyboard()
                    !isMaterialReady
                    !isSubjectfetched
                    !istopicfetched
                    !islearningstarted
                }





//        for (item in filterd_topics) {
//            filterd_topics = item.filter { item.subId == item._id } as ArrayList<Topics>

            }
        }






    }

















    inner class ButtonRecyclerView(var context: Context, var buttons: List<com.example.chat_bot.Rasa.rasaMsg.Button>) : RecyclerView.Adapter<ButtonRecyclerView.ButtonViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
            return ButtonViewHolder(LayoutInflater.from(context).inflate(R.layout.button_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
            val button = buttons[position]
            holder.button.text = button.title
            holder.button.setOnClickListener {

                btnList.add(button)
                sendMessage(button.payload, button.title)


                if(button.payload.contains("/inform_new{\"topic\":"))
                {
                    islearningstarted == true
                    Toast.makeText(requireContext(), "Topic selected", Toast.LENGTH_SHORT).show()
                    val jaga = button.payload.split(":")

                    var su = jaga.last().replace("\"", "")
                    // su.replace("}", "")

                    su =  su.replace("}", "")
                    getQuiz(su)
                    Log.d("jaaga", su.replace("}", ""))
                }



                //val jaga =    button.payload.split(Pattern.compile("/([0-9]+([a-zA-Z]+[0-9]+)+)[a-zA-Z]+/"))





                //getTopicid(jaga.last())

                //  jaga.last().split(Regex("\"[^\"]*\"\\}"))






                //  Toast.makeText(requireContext(), getTopicid(button.payload).toString(), Toast.LENGTH_SHORT).show()



            }
        }

        override fun getItemCount(): Int {
            btnList.isEmpty() ?: return -1
            return btnList.size

        }

        inner class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val button = view.findViewById<Button>(R.id.payloadBtn)
        }
    }
}


