package com.example.chat_bot.Activities.HomePage

import Quest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.example.chat_bot.Activities.activity.QuizActivity
import com.example.chat_bot.R
import com.example.chat_bot.Rasa.Networkings.*
import com.example.chat_bot.Rasa.rasaMsg.BotResponse
import com.example.chat_bot.Rasa.rasaMsg.UserMessage
import com.example.chat_bot.data.*
import com.example.chat_bot.data.tryy.AllQuestion
import com.example.chat_bot.data.tryy.QuestItem
import com.example.chat_bot.databinding.FragmentChatBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.ui.quiz_adapter
import com.example.chat_bot.utils.*
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class ChatFragment : Fragment(), msgAdapter.Callbackinter, quiz_adapter.Callbackinter {


    private lateinit var selected_topicID: String
    private lateinit var adapter: msgAdapter
    private lateinit var msg: String
    private val TAG = "ChatFragment"
    private lateinit var binding: FragmentChatBinding
    private lateinit var language: String
    var messagesList = mutableListOf<Message>()
    var subjects: MutableList<Subjects> = arrayListOf()
    var quizez: ArrayList<Quest> = arrayListOf()
    var question: MutableList<AllQuestion> = arrayListOf()
    var filterd_subjects: MutableList<Subjects> = arrayListOf()
    var quiz: ArrayList<AllQuestion> = arrayListOf()
    var filterd_topics: ArrayList<Topics> = arrayListOf()
    var b: ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()
    var topics: MutableList<Topics> = arrayListOf()
    private var db: DB? = null
    lateinit var viewModel: SEEDSViewModel
    private var isSubjectfetched: Boolean = false
    var istopicfetched: Boolean = false
    var isMaterialReady: Boolean = false
    var islearningstarted: Boolean= false
    var returnedFromquiz: Boolean = false
    var material_Lang_not_known: Boolean = false
    lateinit  var APP_MODE: String
    lateinit var topic_name: String
    lateinit var ageGroup: String
    lateinit var grades: String
    var m_androidId: String ?= null
    lateinit var topicLang: String
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var session: SessionManager
    lateinit var user: List<User>
    private lateinit var materialLang: String
    var quest: ArrayList<QuestItem> = arrayListOf()
    lateinit var wholeQuiz: Any
    lateinit var topic_namez: String
    lateinit var topic_id: String
    var user_id: String = ""
    var username: String =""
    private var url  = ""
    private var accessCode: String = ""
    var required_Access_Code: String= ""
    var Quiz_access: Boolean = false
    lateinit var toast: Toast


    ///////////////////////////////////////////
    //////////RASA////////////////////////////


    private val retroService = api_Rasa.getInstance()
    var msgBtn: ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()

    var iterator: Int = 0

    //////////////////////////////////////


    var isRasa: Boolean = true
    private var isVanilla: Boolean = false




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val sharedPrefs: SharedPreferences = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedPrefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            // if true then change the theme to dark mode
            requireContext().setTheme(R.style.DarkMode)
        } else {
            requireContext().setTheme(R.style.WhiteMode)
        }
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)


        db = this.context?.let { DB(it) }
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(
            SEEDSViewModel::class.java)

        //rasa view model
        session = SessionManager(context as Activity)
        adapter = msgAdapter(this, this.requireContext())
        topic_id = ""
        user_id= ""
        selected_topicID= ""

        GetuserDetails()
        CheckAccessCode()
        checklang()
        getDevID()
        toLowercase()
        checkAppMode()
        isOnline(context as Activity)


        val lottieAnimationView = view?.findViewById<LottieAnimationView>(R.id.typingStatus)

        // load the animation from the JSON file
        lottieAnimationView?.setAnimation(R.raw.typing_animation)
        lottieAnimationView?.playAnimation()

        binding.rvMessages.layoutManager = LinearLayoutManager(this.requireContext())
       // binding.rvMessages.hasFixedSize()

        binding.rvMessages.setOnClickListener{
            binding.etMessage.clearFocus()
            val foc = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.etMessage.windowToken, 0)
        }

        return binding.root
    }



    private fun toLowercase() {
        var trueGreek = "ψευδές".lowercase()
        var trueGreekkk = "Falsch".lowercase()
        var trueGreekk = "Falso".lowercase()

        Log.d("like", trueGreek)
        Log.d("like", trueGreekkk)
        Log.d("like", trueGreekk)



    }

    private fun CheckAccessCode():String {
        accessCode = session.getPrivateMaterialsAccessCode()
        Log.d("ChatFragment", "Access Code $accessCode")
        return accessCode
    }

    fun getQuiz(id: String) {
        viewModel.getQuiz(id)
        viewModel.quizList.observe(viewLifecycleOwner, Observer {
            Log.d(ContentValues.TAG, "OnCreate: $it")



            if (quizez.size>0)
            {
                clearValues()
            }

            quizez.addAll(listOf(it))
            var user: HashMap<String, String> =  session.getUserDetails()

           // Log.d("user_id", user.get("devID").toString())

            user.get("age")



            for (item in quizez)
            {
                for (i in item)
                {

                    Quiz_access = i.access
                    required_Access_Code =  i.accessCode
                    quest.add(i)
                    question.addAll((i.allQuestions))
                    question.sortBy { i -> i.sequence }

                }
            }
                    //  question.filter { i.ageGroup == ageGroup }

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


            Log.d("quizez", question.toString())
            //  Toast.makeText(this.requireContext(), quizez.toString(), Toast.LENGTH_SHORT).show()

        })

        quiz = question as ArrayList<AllQuestion>
        load_material()



        viewModel.errorMessage.observe(this) {
            Toast.makeText(this.requireContext(), "No quiz available", Toast.LENGTH_SHORT).show()

        }


    }

    override fun onPause() {
        super.onPause()

        // Toast.makeText(requireContext(), "onPause", Toast.LENGTH_SHORT).show()
        isOnline(this.requireContext())
        CheckAccessCode()
        checklang()

    }

    override fun onResume() {
        super.onResume()
       //  Toast.makeText(requireContext(), "onResume", Toast.LENGTH_SHORT).show()
        APP_MODE = checkAppMode()
        checklang()
        isOnline(this.requireContext())
        CheckAccessCode()
       // clearValues()
        Log.d("ChatFragment", username)

    }


    private fun checkAppMode(): String {
        var mode: String = session.getAppMode()
        // Toast.makeText(this.requireContext(), mode, Toast.LENGTH_LONG).show()
        return mode


    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {

                //  Toast.makeText(this.requireContext(), "Connection available", Toast.LENGTH_SHORT)
                //    .show()

                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }

        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)


        recyclerView()
        clickEvents()
        //clickevents2()
        checklang()
       // quizDonez()

       var score = session.get_quizDone()
        //sendGreetingMessage()
        if (score == "done")
        {
            quizDone()
        }

        else
        {
            if (isOnline(this.requireContext()))
            {
                sendMessagee("Hello", display = false)
            }
            else{
                customMsg("Hello, Seeds Assistant here!!, How may i help you?", false, msgBtn)
            }

        }

        showLastMessages()

        Log.v(TAG, "In main")

    }

    override fun submitAnswerCallback(openEnded: com.example.chat_bot.data.OpenEnded) {
        TODO("Not yet implemented")
    }

    override fun quizDonez() {

        returnedFromquiz == true
        topic_namez = (context as Activity).intent.getSerializableExtra("Total score").toString()

       // Toast.makeText(context, "HALLOO", Toast.LENGTH_SHORT).show()

        if (topic_namez!= "null")
        {
            Log.d("topic_namez",topic_namez)
            Log.d("topic_namez",session.getObtainedScore())
            Log.d("topic_namez",session.getTOtalScore())
            val obtained_score = session.getObtainedScore()
            val total_score = session.getTOtalScore()

            val scores = "/activity_done{\"score1\":${obtained_score}, \"score2\":${total_score}, \"topic_id\":\"${session.get_topicID()}\", \"topic_completed\":\"${session.get_topic()}\"}"
            returnedFromquiz = true
           // Toast.makeText(context as Activity, scores.toString(), Toast.LENGTH_SHORT).show()

            Log.d("topic_namez",scores)
            filterd_topics.clear()
            msgBtn.clear()
            sendMessagee(scores, display = false)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun quizDone() {


        topic_namez = (context as Activity).intent.getSerializableExtra("Total score").toString()


        if (topic_namez!= "null")
        {
            Log.d("topic_namez",topic_namez)
            Log.d("topic_namez",session.getObtainedScore())
            Log.d("topic_namez",session.getTOtalScore())
            val obtained_score = session.getObtainedScore()
            val total_score = session.getTOtalScore()

            val scores = "/activity_done{\"score1\":${obtained_score}, \"score2\":${total_score}, \"topic_id\":\"${session.get_topicID()}\", \"topic_completed\":\"${session.get_topic()}\"}"
            returnedFromquiz = true
           // Toast.makeText(context as Activity, scores.toString(), Toast.LENGTH_SHORT).show()

            Log.d("topic_namez",scores)
            Log.d("topic_namezZ", msgBtn.size.toString())

            filterd_topics.clear()
            msgBtn.clear()
            sendMessagee(scores, display = false)
           // adapter.notifyDataSetChanged()
            session.quizDone("nope")

            //!session.quizDone()
        }

    }

    private fun checklang() {
        language = Lingver.getInstance().getLanguage()
        if (language == "de") {url = "https://l0mgxbahu7.execute-api.eu-central-1.amazonaws.com" }
        else if (language == "es") { url = "https://l21uryvtf9.execute-api.eu-central-1.amazonaws.com"}
        else if (language == "el") {url = "https://i9490x4qog.execute-api.eu-central-1.amazonaws.com"}
        else url = "https://ig1mceza29.execute-api.eu-central-1.amazonaws.com/"
        Log.d("ChatFragment", "language = $language")
    }
    private fun process_request(response: String) {

        fetch_subjects()


    }

    private  fun fetch_subjects() {

        viewModel.subjectList.observe(viewLifecycleOwner, Observer {
            Log.d(ContentValues.TAG, "OnCreate: $it")

            subjects.addAll(it)
            subjects.size

            if (it.size<1)
            {
                customMsg("Subject List is empty (fetch_subjects)", false, msgBtn)
                !istopicfetched
                !isMaterialReady
                !isSubjectfetched
                return@Observer
            }
            else
                isSubjectfetched = true

        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(this.requireContext(), "Error fetching subjects", Toast.LENGTH_SHORT).show()
            Log.d(ContentValues.TAG, "OnCreate: $it")

            isSubjectfetched = false
            istopicfetched = false

            if (language== "en")
            {
                customMsg("I am facing problems at the moment", false, msgBtn)
            }
            if (language== "de")
            {
                customMsg("Ich habe derzeit Probleme", false, msgBtn)
            }
            if (language== "es")
            {
                customMsg("Actualmente tengo problemas", false, msgBtn)
            }

        })

        viewModel.getAllSubjects()

    }
    private fun suggest_topic(filterd_topicss: ArrayList<Topics>) {
        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()


        //customMsg("you have following option")

        GlobalScope.launch {
            //response delay
            delay(2000)
            withContext(Dispatchers.Main) {

                if (filterd_topicss.isEmpty())
                {
                    customMsg("This topic is not found", false, msgBtn)
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

                    }

                    adapter.publishSuggestion(filterd_topicss)

                    botResponse("please_publish_sugesstion", true)
                }

            }
        }
    }
    private  fun initiate_subject_filtration(mk: String) {
        var found: Subjects? =  subjects.find { i -> i.subject.trim().lowercase() == mk.trim().lowercase() }
        if (found != null) {

            filterd_subjects.addAll(listOf(found))

            fetch_topics(filterd_subjects, mk)

        }

        else{
            isSubjectfetched = true
            istopicfetched = false
            isMaterialReady = false

            if (language== "en")
            {
                customMsg("Sorry this one is not available", false, msgBtn)


            }
            if (language== "de")
            {
                customMsg("Dieses Thema ist leider nicht verfügbar", false, msgBtn)
            }
            if (language== "es")
            {
                customMsg("Lo sentimos, este tema no está disponible", false, msgBtn)
            }
            customMsg("Try looking for some other subjects", false, msgBtn)

        }



    }
    private  fun fetch_topics(filterd_subjects: MutableList<Subjects>, mk: String) {

        //topics from database
        viewModel.topicListss.observe(viewLifecycleOwner) {
            Log.d(ContentValues.TAG, "OnCreate: $it")
            var datasize = it.size
            istopicfetched = true

            if (it.size<1)
            {
                customMsg("No topic found with $mk subject", false, msgBtn)
                !isSubjectfetched
                !isMaterialReady
                !istopicfetched
                return@observe
            }
            else

                for (item in filterd_subjects) {

                    if (materialLang != "null")
                    {filterd_topics = it.filter { it.subId == item._id  && it.ageId == ageGroup

                            && it.language == materialLang} as ArrayList<Topics>}
                    else
                    {filterd_topics = it.filter { it.subId == item._id  && it.ageId == ageGroup} as ArrayList<Topics>}




                    Log.d("joko", ageGroup)
                    suggest_topic(filterd_topics)
                }




            Log.d("filter_topics", filterd_topics.toString())
        }
        viewModel.getAllTopics()
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(this.requireContext(), "Error fetching topics", Toast.LENGTH_SHORT).show()
            istopicfetched = false
            isSubjectfetched = false
            isMaterialReady = false

            if (language== "en")
            {
                customMsg("I am facing problems at the moment", false, msgBtn)
            }
            if (language== "de")
            {
                customMsg("Ich habe derzeit Probleme", false, msgBtn)
            }
            if (language== "es")
            {
                customMsg("Actualmente tengo problemas", false, msgBtn)
            }
        })
    }
    fun loadNext(input: String): Boolean {
        val regex = Regex(pattern = "/next_option\\{\"subj\":\"None\"\\}", options = setOf(RegexOption.IGNORE_CASE))
        return regex.matches(input)
    }

    override fun passResultCallback(message: Topics) {
        isMaterialReady = true

        msg = message.topic
        var id: String = message._id

        val payload = id.split(":")
        Log.d("payloadss", payload.toString())

        session.save_topic(msg)

        customMsg(msg, false, msgBtn)



        if (loadNext(message._id))
        {

            sendMessagee(message._id, display = false)

        }
        else
        {
            if(message._id.contains("/inform_new{\"topic\":") ||
                message._id.contains("/inform_new_topic{\"topic\":") ||
                message._id.contains("/inform_new_direct_topic{\"topic\":"))

            {
                islearningstarted == true

                val jaga = message._id.split(":")

                var su = jaga.last().replace("\"", "")

                su =  su.replace("}", "")

                session.save_topicID(su)
                selected_topicID = su
                getQuiz(su.trim())
                clearValues()

             Log.d("jaaga", su.replace("}", ""))
            }
            else if (message._id.contains("/inform_new{\"subject\":"))
            {
                Send_filterInfo_toRASA(message._id)
            }
            else
            {
                sendMessagee(message._id, display = false)

            }

        }

        adapter.notifyDataSetChanged()

    }

    private fun Send_filterInfo_toRASA(_id: String) {

        val payload = _id.split(":")

        var subjectName = payload.last().replace("\"", "")

        subjectName =  subjectName.replace("}", "")


        var userDetails = session.getUserDetails()

        var userAge = userDetails.get("age")
        var userGrade = userDetails.get("grade")
        var PreferedMaterialslang = session.get_materialLangPref()
        if (PreferedMaterialslang == null || PreferedMaterialslang == "null" || PreferedMaterialslang.isEmpty())
        {
            PreferedMaterialslang = "English"
        }

        var newpayload = "/inform_new{\"subject\":\"${subjectName}\", \"material_language\":\"${PreferedMaterialslang}\", \"age\":\"$userAge\", \"grade\":\"${userGrade}\"}"
        sendMessagee(newpayload, display = false)
        Log.d("ChatFragment", newpayload.toString())
    }


    private fun load_material() {

        if (filterd_topics.isEmpty()){

            customMsg("no topics found", false, msgBtn)
            !istopicfetched
            !isMaterialReady
            !isSubjectfetched
            return

        }
        else if (!filterd_topics.isEmpty())
        {
            GlobalScope.launch {
                delay(3000)
                withContext(Dispatchers.Main) {


                    if (quiz.isEmpty())
                    {
                        Toast.makeText(context, "no quiz available ", Toast.LENGTH_SHORT).show()
                        loadOptions()
                    }

                    else
                    {

                        if (Quiz_access)
                        {
                            if (CheckAccessCode()== required_Access_Code)
                            {
                                GotoSolveActivities()
                            }
                            else
                            {
                                Toast.makeText(context, "Quiz is private", Toast.LENGTH_SHORT).show()
                                clearValues()
                                ask_for_access_code()
                            }

                        }
                        else
                        {
                         GotoSolveActivities()
                        }

                    }

                }
            }
        }

    }

    private fun loadOptions()
    {
        binding.etMessage.isEnabled = true
        binding.btnSend.isEnabled = true
        clearValues()

        if (language== "en")
        {
            sendMessagee("/user_stop{\"subject\":\"STOP\"}", display = false)
        }
        else if(language== "de")
        {

            sendMessagee("Ich möchte lernen", display = false)
        }
        else if (language== "es")
        {

            sendMessagee("quiero aprender", display = false)
        }
        else if(language == "el")
        {
            sendMessagee("Θέλω να μάθω", display = false)
        }
    }

     private fun clearValues()
    {
        filterd_topics.clear()
        question.clear()
        quizez.clear()
        quiz.clear()
        quest.clear()
    }

    private fun ask_for_access_code()
    {

        binding.etMessage.isEnabled = true
        binding.btnSend.isEnabled = true
        //clearValues()

        if (language== "en")
        {
            customMsg("Enter the password in settings and try again.", false, msgBtn)
            sendMessagee("i want to learn", display = false)
        }
        else if(language== "de")
        {
            customMsg("Bitte geben Sie das Passwort in den Einstellungen ein und versuchen Sie es dann erneut",  false, msgBtn)
            sendMessagee("Ich möchte lernen", display = false)
        }
        else if (language== "es")
        {
            customMsg("Por favor, introduzca la contraseña en los ajustes y vuelva a intentarlo", false, msgBtn)
            sendMessagee("quiero aprender", display = false)
        }
        else if(language == "el")
        {
            sendMessagee("Θέλω να μάθω", display = false)
            customMsg("Εισάγετε τον κωδικό πρόσβασης στις ρυθμίσεις και δοκιμάστε ξανά. ", false, msgBtn)
        }

    }

    private fun GotoSolveActivities()
    {

        val intent = Intent(context, QuizActivity::class.java).apply {


            session.save_topic(msg)


            // putExtra("filtered_topics", filterd_topics)
            putExtra("filtered_topics", filterd_topics)
            putExtra("selected_topic", msg)
            putExtra("Quiz", quiz)
            putExtra("Whole Quest", quest)
            (context as Activity).finish()
        }
        startActivity(intent)
        hideKeyboard()
        !isMaterialReady
        !isSubjectfetched
        !istopicfetched
        !islearningstarted
    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun hideKeyboard() {
        this.view?.findFocus()?.let { view ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    private fun customMsg(message: String, _yo: Boolean, buttons: List<com.example.chat_bot.Rasa.rasaMsg.Button>) {
        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()
        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                if (_yo)
                {
                    adapter.insertMessage(Message(message, Constants.SND_ID, timeStamp, true, "",buttons, username))

                    db?.insertMessage(Message(message, Constants.SND_ID, timeStamp, true, "",buttons, username))
                }
                else{
                    adapter.insertMessage(Message(message, Constants.SND_ID, timeStamp, false, "",buttons, username))

                    db?.insertMessage(Message(message, Constants.SND_ID, timeStamp, false, "",buttons, username))
                }


                binding.typingStatus.cancelAnimation()
                binding.typingStatus.visibility = View.GONE
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }

    }

    private fun recyclerView() {
        //binding.rvMessages.apply {
        adapter = msgAdapter(this, requireContext())
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(this.context)
        Log.v(TAG, "ReCYCLE")

        // }

    }

    @SuppressLint("HardwareIds")
    private fun getDevID() {
        m_androidId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)

       m_androidId =  "$m_androidId/" + UUID.randomUUID().toString()
        //Toast.makeText(this, m_androidId.toString(), Toast.LENGTH_SHORT).show()
        Log.d("DevID", m_androidId.toString())
    }

    fun sendMessagee(message: String, alternative: String = "", display: Boolean = true) {

        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()

        val displayedMessage = if (alternative.isNullOrEmpty()) message else alternative
        val timeStamp = Time.timeStamp()

        binding.btnSend.isEnabled = true
        binding.etMessage.isEnabled = true

        if (filterd_topics.size > 0) {
            filterd_topics.clear()
           // clearValues()
        }
        Log.d("user_id", m_androidId.toString())

        var userMessage = UserMessage(m_androidId.toString(), message)

        Log.d("what is message", message)

        if (display) {
            db!!.insertMessage(Message(message, Constants.SND_ID, timeStamp, false, "", msgBtn, username))
            //  }

            adapter.insertMessage(
                Message(
                    message,
                    Constants.SND_ID,
                    timeStamp,
                    false,
                    "",
                    msgBtn, ""
                )
            )

            binding.typingStatus.visibility = View.GONE
            binding.typingStatus.cancelAnimation()

            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

        }

        GlobalScope.launch {
            delay(2000)
            withContext(Dispatchers.Main) {

                val okHttpClient = OkHttpClient()
                val retrofit = Retrofit.Builder().baseUrl(url).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                val messageSender = retrofit.create(api_Rasa::class.java)
                val response = messageSender.sendMessage(userMessage)

                response.enqueue(object : Callback<ArrayList<BotResponse>> {

                    override fun onResponse(
                        call: Call<ArrayList<BotResponse>>,
                        response: Response<ArrayList<BotResponse>>
                    ) {
                        if (response.body() != null && response.body()!!.size != 0) {
                            for (i in 0 until response.body()!!.size) {
                                val message = response.body()!![i]


                                try {

                                    if (message.text.isNotEmpty()) {
                                        db!!.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                false,
                                                "",
                                                msgBtn, username
                                            )
                                        )
                                        //  }

                                        adapter.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                false,
                                                "",
                                                msgBtn, username
                                            )
                                        )

                                        //   SaveMessagesToLocalDb(msg, Constants.RCV_ID)

                                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)


                                    } else if (message.image.isNotEmpty()) {

                                    }
                                } catch (e: Exception) {
                                }

                                try {
                                    if (message.buttons != null) {

                                        msgBtn = message.buttons as ArrayList<com.example.chat_bot.Rasa.rasaMsg.Button>
                                        adapter.insertMessage(
                                            Message(
                                                message.text,
                                                Constants.RCV_ID,
                                                timeStamp,
                                                true,
                                                "",
                                                msgBtn, username
                                            )
                                        )

                                        binding.btnSend.isEnabled = false
                                        binding.etMessage.isEnabled = false

                                        for (item in message.buttons) {

                                            filterd_topics.distinct()
                                            filterd_topics.addAll(
                                                (listOf(
                                                    Topics(
                                                        item.payload,
                                                        item.title,
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        "",
                                                        ""
                                                    )
                                                ))
                                            )
                                            filterd_topics.size


                                        }
                                        //  adapter.insertMessage(Message(message.text, Constants.SND_ID, timeStamp,true, "",msgBtn))

                                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)


                                       // adapter.publishSuggestion(filterd_topics)
                                        Log.d("btnz", message.buttons.toString())



                                        //  Message(message="", id= "", buttons = msgBtn , has_suggestion = false, time = "", topic = "")

                                    }
                                } catch (e: Exception) {
                                }
                                // adapter.notifyItemInserted(binding.rvMessages.size)


                            }
                        } else {
                            val message = "Sorry, something went wrong:\nReceived empty response."


                            db!!.insertMessage(
                                Message(
                                    message,
                                    Constants.SND_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn, username
                                )
                            )
                            //  }

                            adapter.insertMessage(
                                Message(
                                    message,
                                    Constants.SND_ID,
                                    timeStamp,
                                    false,
                                    "",
                                    msgBtn, username
                                )
                            )

                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)



                        }

                        binding.typingStatus.visibility = View.GONE
                        binding.typingStatus.cancelAnimation()
                    }

                    override fun onFailure(call: Call<ArrayList<BotResponse>>, t: Throwable) {
                        t.printStackTrace()
                        val message = "Sorry, something went wrong:\n" + t.message
                        db!!.insertMessage(
                            Message(
                                message,
                                Constants.SND_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn, username
                            )
                        )
                        //  }

                        adapter.insertMessage(
                            Message(
                                message,
                                Constants.SND_ID,
                                timeStamp,
                                false,
                                "",
                                msgBtn, ""
                            )
                        )

                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        binding.typingStatus.visibility = View.GONE
                        binding.typingStatus.cancelAnimation()

                    }


                })

            }
        }

    }


    private fun clickEvents() {

        binding.btnSend.setOnClickListener {

            GlobalScope.launch {
                delay(200)
                withContext(Dispatchers.Main) {



                    val msg = binding.etMessage .text.toString().trim()
                    //  smoothScroller.setTargetPosition(adapter.itemCount -1)
//            msgRV.getLayoutManager()?.startSmoothScroll(smoothScroller)

                    if (msg != "") {
                        if (checkAppMode() == "vanilla")
                        {
                            VanillasendMessage()
                        }
                        else{
                            sendMessage()
                        }

                        // getQuiz("62fe1bb09c4afe7b1a0bedfd")
                        binding.etMessage.setText("")
                        //     hideKeyboard()
                    } else {
                        Toast.makeText(requireContext(), "Please enter a message.", Toast.LENGTH_SHORT).show()
                    }

                }
            }



        }


        //Send a message
        binding.btnSend.setOnClickListener {

            if (checkAppMode() == "vanilla")
            {
                VanillasendMessage()
            }
            else

                sendMessage()

        }
        binding.etMessage.setOnClickListener {
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    fun sendMessage() {
        var user: HashMap<String, String> = session.getUserDetails()
        var age =  user.getValue("age")
        var grade = user.getValue("grade")

        msg = binding.etMessage.text.toString().trim()
        val timeStamp = Time.timeStamp()
        val userMessage = UserMessage(Constants.SND_ID,msg)


        if (!material_Lang_not_known)
        {
            val lang: List<Any> = listOf("German", "English", "Spanish", "Greek", "german", "english", "spanish", "greek")


            val match = lang.any() { msg.contains(msg, ignoreCase = true) }
            val matchs = msg.filter() { msg -> msg.equals(lang) }


        }


        if (msg.isNotEmpty()) {

            binding.etMessage.setText("")

            //  Toast.makeText(this.requireContext(), isOnline(this.requireContext()).toString(), Toast.LENGTH_SHORT).show()

            if (isOnline(this.requireContext()))
            {

                sendMessagee(msg)
                hideKeyboard()
            }
            else
            {
                //Adds it to our local list

                db!!.insertMessage(Message(msg, Constants.SND_ID, timeStamp, false, "",msgBtn, username))
                //  }

                adapter.insertMessage(Message(msg, Constants.SND_ID, timeStamp,false, "",msgBtn, username))

                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }


            if (!isMaterialReady)
            {
                if(isSubjectfetched == true)
                {
                    initiate_subject_filtration(msg)
                    return
                }

            }

            if (isMaterialReady){
                if(!filterd_topics.isEmpty())
                {
                    for (item in filterd_topics)
                    {
                        topic_name = item.topic
                        topic_id = item._id

                        if (msg == topic_name && ageGroup == age && grades == grade )
                        {
                            //  load_material()
                            return
                        }
                        else
                        {
                            Toast.makeText(this.requireContext(), "Error! Loading materials", Toast.LENGTH_SHORT).show()
                            return
                        }

                    }
                }
            }
            if (!isOnline(this.requireContext()))
            {
                botResponse(msg, false)
            }

        }
    }


    fun VanillasendMessage() {
        var user: HashMap<String, String> = session.getUserDetails()
        var age =  user.getValue("age")
        var grade = user.getValue("grade")
        msg = binding.etMessage.text.toString().trim()
        val timeStamp = Time.timeStamp()

        if (msg.isNotEmpty()) {

            binding.etMessage.setText("")
            //Adds it to our local list


            db!!.insertMessage(Message(msg, Constants.SND_ID, timeStamp, false, "", msgBtn, username))
            //  }

            adapter.insertMessage(Message(msg, Constants.SND_ID, timeStamp,false, "", msgBtn, username))

            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)


            if (!isMaterialReady)
            {
                if(isSubjectfetched == true)
                {
                    initiate_subject_filtration(msg)
                    return
                }

            }

            if (isMaterialReady){
                if(!filterd_topics.isEmpty())
                {
                    for (item in filterd_topics)
                    {
                        topic_name = item.topic
                        topic_id = item._id

                        Log.d("ageGroupp", age.toString())
                        Log.d("ageGroupp", ageGroup.toString())

                        if (msg == topic_name && ageGroup == age && grades == grade )
                        {
                            load_material()
                            return
                        }
                        else
                        {
                            Toast.makeText(this.requireContext(), "Error! Loading materials", Toast.LENGTH_SHORT).show()
                            return
                        }

                    }
                }
            }


            else{botResponse(msg, false)}

        }
    }



    private fun botResponse(message: String, _yo:Boolean) {
        val timeStamp = Time.timeStamp()
        binding.typingStatus.visibility = View.VISIBLE
        binding.typingStatus.playAnimation()

        GlobalScope.launch {
            //response delay
            delay(2000)
            withContext(Dispatchers.Main) {

                if (isVanilla == false)
                {
                    if (isRasa)
                    {
                        if (_yo)
                        {
                            val response = Bot_replies.basicResponses(message, false)
                            db!!.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp, false, "",msgBtn, username))
                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp,false, "",msgBtn, ""))

                            delay(2000)
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,true, "",msgBtn, ""))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
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

                            //  val status = db!!.insertMessage(
                            Message(
                                response as String,
                                Constants.RCV_ID,
                                timeStamp,
                                false, ""
                                ,msgBtn, username)


                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response, Constants.RCV_ID, timeStamp, false, "",msgBtn, username))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE

                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)


                        }
                    }
                }

                else
                {
                    if (language == "de") {
                        //setbottoGerman()

                        if (_yo)
                        {
                            //Gets the response
                            val response = Bot_replies_de.basicResponses(message, false)
                            db!!.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp, false, "",msgBtn,username))
                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,false, "",msgBtn, username))

                            delay(2000)
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,true, "",msgBtn, ""))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }
                        else
                        {
                            var ans: Boolean
                            //Gets the response
                            val res: Any = Bot_replies_de.basicResponses(message, false)
                            var response = Bot_replies_de.basicResponses(message,false)
                            ans = res.toString().contains("12")

                            if ( ans)
                            {
                                response = res.toString().replace("12", "")

                                process_request(response)
                                islearningstarted == false

                            }

                            when (response) {
                                Constants.SEEDS -> {


                                }
                            }


                            // val status = db!!.insertMessage(
                            Message(
                                response as String,
                                Constants.RCV_ID,
                                timeStamp,
                                false, ""
                                ,msgBtn, username)
                            //)

                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,false,"",msgBtn, username))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE

                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)




                        }
                        return@withContext
                    }

                    if (language == "es") {
                        //setbottoGerman()

                        if (_yo)
                        {
                            //Gets the response
                            val response = Bot_replies_es.basicResponses(message, false, "")
                            db!!.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp, false, "",msgBtn,username))
                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,false, "",msgBtn, username))

                            delay(2000)
                            adapter.insertMessage(Message(response, Constants.RCV_ID, timeStamp,true, "",msgBtn, username))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE
                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
                        }
                        else
                        {
                            var ans: Boolean
                            //Gets the response
                            val res: Any = Bot_replies_es.basicResponses(message, false, "")
                            var response = Bot_replies_es.basicResponses(message, false, "")
                            ans = res.toString().contains("12")
                            if ( ans)
                            {
                                response = res.toString().replace("12", "")

                                islearningstarted == false
                                process_request(response)
                            }

                            when (response) {
                                Constants.SEEDS -> {


                                }
                            }


                            //   val status = db!!.insertMessage(
                            Message(
                                response as String,
                                Constants.RCV_ID,
                                timeStamp,
                                false, ""
                                ,msgBtn,"")
                            //   )

                            //Inserts our message into the adapter
                            adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,false, "",msgBtn, username))

                            binding.typingStatus.cancelAnimation()
                            binding.typingStatus.visibility = View.GONE

                            //Scrolls us to the position of the latest message
                            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)


                        }
                        return@withContext
                    }


                    if (_yo)
                    {
                        //Gets the response
                        val response = Bot_replies.basicResponses(message, false)
                        db!!.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp, false, "",msgBtn,username))
                        //Inserts our message into the adapter
                        adapter.insertMessage(Message(response as String, Constants.RCV_ID, timeStamp,false, "",msgBtn,username))

                        delay(2000)
                        adapter.insertMessage(Message(response , Constants.RCV_ID, timeStamp,true, "",msgBtn, username))

                        binding.typingStatus.cancelAnimation()
                        binding.typingStatus.visibility = View.GONE
                        //Scrolls us to the position of the latest message
                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
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


                        //  val status = db!!.insertMessage(
                        Message(
                            response as String,
                            Constants.RCV_ID,
                            timeStamp,
                            false, ""
                            ,msgBtn,username)


                        //Inserts our message into the adapter
                        adapter.insertMessage(Message(response, Constants.RCV_ID, timeStamp, false, "",msgBtn,username))

                        binding.typingStatus.cancelAnimation()
                        binding.typingStatus.visibility = View.GONE

                        //Scrolls us to the position of the latest message
                        binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

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
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun showLastMessages() {
        Log.d("ChatFragment", "showLastMessages()-> ${messagesList.size}")
        Log.d("ChatFragment", "username-> ${username}")
        Log.d("ChatFragment", "showLastMessages()-> ${messagesList}")

        messagesList = db!!.getMessages()

        messagesList.filter { i->i.username == username }

        // Collections.reverse(messagesList)
        adapter.setMessages(messagesList)
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(this.context)
        adapter.notifyDataSetChanged()


    }

    private fun GetuserDetails()
    {
        val user= session.getUserDetails()

        username = user.get("name").toString()


    }

}

