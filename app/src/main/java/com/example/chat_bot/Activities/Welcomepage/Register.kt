package com.example.chat_bot.Activities.Welcomepage

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.Activities.HomePage.MainActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.User
import com.example.chat_bot.data.Userinfo
import com.example.chat_bot.databinding.ActivityRegisterBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.utils.SessionManager
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.launch
import java.util.*


class Register : AppCompatActivity() {


    private lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: SEEDSViewModel
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var session: SessionManager
    lateinit var grade_spinner: AutoCompleteTextView
    private var USER = "M-f8f2e818-808f-"
    var user_name:String = ""
    var user_age: String = ""
    var user_country: String = ""
    var materialLang:String = ""
    private lateinit var user_language: String
    var user_grade: String = ""
    var m_androidId: String ?= null
    private lateinit var language: String
    private lateinit var alreadyUserbtn: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        language = ""

        USER += getDevID()

        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(SEEDSViewModel::class.java)
        session = SessionManager(applicationContext)

        user_language = ""
        checkLogin()

        binding.btnReg.setOnClickListener{
            if (isOnline(applicationContext))
            {
                dologin()
            }
            else
            {
                checkInternet()
            }


        }

        if (isOnline(this))
        {
            //Grades from database
            viewModel.gradeList.observe(this, Observer {
                Log.d(TAG, "OnCreate: $it")
                var datasize = it.size
                var mil: MutableList<String> = arrayListOf()

                for (item in it)
                {
                    mil.add(item.grade)
                    setgrade(mil)
                }
            })

            //Age groups from database
            viewModel.agegrouplist.observe(this, Observer {
                Log.d(TAG, "OnCreate: $it")
                var datasize = it.size
                var AGEGroupslist: MutableList<String> = arrayListOf()

                for (item in it)
                {
                    AGEGroupslist.add(item.age)
                    setAGEGROUPS(AGEGroupslist)
                }
            })


            viewModel.errorMessage.observe(this, Observer {
                Toast.makeText(this, "Error went", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "OnCreate: $it")
                //binding.loadingProgress.visibility = View.GONE
            })
            viewModel.getAllGrades()
            viewModel.getAllAgeGroups()
        }

        else
        {
            if (language== "en")
            {
                Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
            }
            if (language== "de")
            {
                Toast.makeText(this, "Bitte überprüfen Sie Ihre Internetverbindung", Toast.LENGTH_SHORT).show()
            }
            if (language== "es")
            {
                Toast.makeText(this, "Por favor, compruebe su conexión a Internet", Toast.LENGTH_SHORT).show()
            }
            if (language== "el")
            {
                Toast.makeText(this, "Ελέγξτε τη σύνδεσή σας στο διαδίκτυο", Toast.LENGTH_SHORT).show()
            }
        }

        alreadyUserLogin()
        keyboardfocus()

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun setMateriallang() {

        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the language spinner
        val lang_spinner = binding.materiallangBtn
        if (lang_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.material_lang_dropdown, languages)
            lang_spinner.setAdapter(adapter)

            lang_spinner.setOnClickListener {

                it.hideKeyboard()
            }

            lang_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->

                materiallang(languages[position], adapter)
                user_language = languages[position]
            }

        }
    }


    private fun setlang() {
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)

        // access the language spinner
        val lang_spinner = binding.langBtnn
        if (lang_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.lang_dropdown, languages)
            lang_spinner.setAdapter(adapter)

            lang_spinner.setOnClickListener {

                it.hideKeyboard()
            }
            lang_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->

                user_language = languages[position]
                lang(languages[position],adapter)

            }

        }
    }

    override fun onResume() {
        super.onResume()
        setlang()
        setMateriallang()

    }


    private fun setgrade(it: MutableList<String>) {
        grade_spinner = binding.classBtn
        // access the language spinner
        if (grade_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.age_drop_down, it)
            grade_spinner.setAdapter(adapter)
            adapter.notifyDataSetChanged()

            grade_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                adapter.notifyDataSetChanged()
                user_grade = it[position]
            }
         }

    }

    private fun setAGEGROUPS(it: MutableList<String>) {

        // access the language spinner
        val age_spinner = binding.ageBtn
        if (age_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.age_drop_down, it)
            age_spinner.setAdapter(adapter)
            adapter.notifyDataSetChanged()

            age_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->

                user_age = it[position]
                adapter.notifyDataSetChanged()

            }
        }

    }
    private fun materiallang(lang: String, adapter: ArrayAdapter<String>){

        if(lang == "Deutsch")
        {
            recreate()
            adapter.notifyDataSetChanged()
        }
        else if(lang == "Español")
        {
            recreate()
            adapter.notifyDataSetChanged()
        }
        else if(lang == "English")
        {

            recreate()
            adapter.notifyDataSetChanged()
        }

        else if(lang == "Ελληνικά")
        {
            recreate()
            adapter.notifyDataSetChanged()
        }
        else
        adapter.notifyDataSetChanged()
    }



    private fun lang(lang: String, adapter: ArrayAdapter<String>){


        if(lang == "Deutsch")
        {

            Lingver.getInstance().setLocale(this, "de")
           // session.savelanguagePref(Lingver.getInstance().setLocale(this, "de").toString())
            recreate()
            adapter.notifyDataSetChanged()

        }
        else if(lang == "Español")
        {

            Lingver.getInstance().setLocale(this, "es")
           // session.savelanguagePref(Lingver.getInstance().setLocale(this, "es").toString())
            recreate()
            adapter.notifyDataSetChanged()

        }
        else if(lang == "English")
        {

            Lingver.getInstance().setLocale(this, "en")
            val eng = Lingver.getInstance().getLanguage()
            session.savelanguagePref(eng)
            recreate()
            adapter.notifyDataSetChanged()

        }

        else if(lang == "Ελληνικά")
        {

            Lingver.getInstance().setLocale(this, "el")
            val eng = Lingver.getInstance().getLanguage()
            session.savelanguagePref(eng)
            recreate()
            adapter.notifyDataSetChanged()


        }
        else
            session.savelanguagePref(Lingver.getInstance().getLanguage()).toString()
        adapter.notifyDataSetChanged()

    }

    private fun getDevID() {
        m_androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        m_androidId =  "$m_androidId/" + UUID.randomUUID().toString()

        //Toast.makeText(this, m_androidId.toString(), Toast.LENGTH_SHORT).show()
        Log.d("DevID", m_androidId.toString())
    }

    private fun checkLogin() {
        checkInternet()
        if(session.isLoggedIn())
        {
            Log.d("details",session.getUserDetails().toString())
            val intent = Intent(this, MainActivity::class.java)
                .setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("success"))
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

    }

    private fun checkInternet() {
        if (language== "en")
        {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()

        }
        if (language== "de")
        {
            Toast.makeText(this, "Bitte überprüfen Sie Ihre Internetverbindung", Toast.LENGTH_SHORT).show()

        }
        if (language== "es")
        {
            Toast.makeText(this, "Por favor, compruebe su conexión a Internet", Toast.LENGTH_SHORT).show()

        }
        if (language== "el")
        {
            Toast.makeText(this, "Ελέγξτε τη σύνδεσή σας στο διαδίκτυο", Toast.LENGTH_SHORT).show()

        }
    }

    private fun dologin() = if (isOnline(applicationContext))
    {
        user_name = binding.usernameEt.text.toString().trim()
        user_age = binding.ageBtn.text.toString().trim()
        user_language = binding.langBtnn.text.toString().trim()
        user_grade = binding.classBtn.text.toString().trim()
        materialLang = binding.materiallangBtn.text.toString().trim()

        user_country = "Germany"

        user_grade

        val user = Userinfo(user_name, user_age,  user_country,  user_grade, user_language, m_androidId.toString())

        val user1 = User(user_name, user_age,  user_country,  user_grade, user_language, m_androidId.toString(), materialLang)

        if (binding.usernameEt.text == null || user_name == "")
        {
            Toast.makeText(applicationContext, "Username required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.ageBtn.text == null || user_age == "")
        {
            Toast.makeText(applicationContext, "Age required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.classBtn.text == null || user_grade == "")
        {
            Toast.makeText(applicationContext, "Grade required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.materiallangBtn.text == null || user_language == "")
        {
            Toast.makeText(applicationContext, "Material Language required", Toast.LENGTH_SHORT).show()
        }
        else
        {
            viewModel.create_user(user)
            viewModel.myresponse.observe(this, Observer {  response->


                if (response.code() == 400 )
                {
                    Toast.makeText(this, "User already exist!", Toast.LENGTH_SHORT).show()
                }
                if (response.code() == 500 )
                {
                    Toast.makeText(this, "The Username must contain atleast 5 letters", Toast.LENGTH_SHORT).show()
                    //viewModel.myresponse.postValue(null)
                }

                else if (response.body()!= null)
                {
                    session.createLoginSession(user_name, m_androidId.toString())
                    session.save_details(user_name, user_age, user_grade, materialLang)
                    saveInfoToLocalDB(user1)
                    val intent = Intent(this, Login::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(Uri.parse("success"))
                    startActivity(intent)
                    finish()
                }

                response.code()
                response.body()
            })
        }

    }

    else
    {
        checkInternet()
    }

    private fun saveInfoToLocalDB(user: User) {
        val dao: SeedsDao = SeedsDatabase.getInstance(this).seedsDao
        lifecycleScope.launch { dao.insertUser(user) }
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
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

    fun alreadyUserLogin(){

        alreadyUserbtn = findViewById<TextView>(R.id.Already_user_login_btn)

        alreadyUserbtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@Register, WelcomePage::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun keyboardfocus()
    {
        binding.classBtn.setOnClickListener {
            binding.usernameEt.clearFocus()
            val foc = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.usernameEt.windowToken, 0)
        }

         binding.ageBtn.setOnClickListener {
            binding.usernameEt.clearFocus()
            val foc = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.usernameEt.windowToken, 0)
        }

        binding.backgroundImage?.setOnClickListener {
            binding.usernameEt.clearFocus()
            val foc = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.usernameEt.windowToken, 0)
        }

        binding.ageBtn?.setOnClickListener {
            binding.usernameEt.clearFocus()
            val foc = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.usernameEt.windowToken, 0)
        }
        binding.classBtn?.setOnClickListener {
            binding.usernameEt.clearFocus()
            val foc = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            foc.hideSoftInputFromWindow(binding.usernameEt.windowToken, 0)
        }
    }
}