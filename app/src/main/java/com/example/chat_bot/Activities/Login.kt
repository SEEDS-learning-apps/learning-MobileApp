package com.example.chat_bot.Activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.Secure
import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.Entities.OnlineUserData
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.User
import com.example.chat_bot.data.Userz
import com.example.chat_bot.databinding.ActivityLoginBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
import com.example.chat_bot.utils.SessionManager
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.launch
import java.lang.Exception


class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: SEEDSViewModel
    private val retrofitService = SEEDSApi.getInstance()
    lateinit var session: SessionManager
    var user_name:String = ""
    var password: String = ""
    var m_androidId: String ?= null
    private lateinit var language: String
    private lateinit var materialLanguage: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        materialLanguage = ""
        session = SessionManager(applicationContext)


        checklang()
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService)))
            .get(SEEDSViewModel::class.java)


        binding.btnLogin.setOnClickListener{
            if (isOnline(this))
            {
                dologin()
            }
            else
            {
                if (language== "en")
                {
                    materialLanguage = "English"
                    Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()

                }
                if (language== "de")
                {
                    materialLanguage = "Deutsch"
                    Toast.makeText(this, "Bitte überprüfen Sie Ihre Internetverbindung", Toast.LENGTH_SHORT).show()

                }
                if (language== "es")
                {
                    materialLanguage = "español"
                    Toast.makeText(this, "Por favor, compruebe su conexión a Internet", Toast.LENGTH_SHORT).show()

                }
                if (language== "el")
                {
                    materialLanguage = "ελληνικά"
                    Toast.makeText(this, "Ελέγξτε τη σύνδεσή σας στο διαδίκτυο", Toast.LENGTH_SHORT).show()

                }
            }


//            val intent = Intent(this@Login, HomeActivity::class.java)
//                .setAction(Intent.ACTION_VIEW)
//                .setData(Uri.parse("success"))
//            startActivity(intent)
//            finish()
        }

        binding.registerTv.setOnClickListener { register() }
        // var conn: InternetConnection = fal
        // conn.isOnline(this)
        getDevID()
        hideActionBar()

    }

    private fun checklang() {
        language = Lingver.getInstance().getLanguage()
        Log.d("ChatFragment", "language = $language")
    }

    private fun register() {
        if (isOnline(this))
        {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show()
        }

    }


//    private fun dologin() {
//        user_name = binding.usernameEt.text.toString().trim()
//
//
//
//        val user = User(user_name, "",  "germany", "", "", "" )
//
//        if (isOnline(this))
//        {
//            if (user_name != "") {
//                viewModel.userList.observe(this, Observer {  response->
//
//                    Log.d("loginn", response.token)
//                    Log.d("loginn", response.auth.toString())
//
//
//
////                    if (response.code()== 404)
////                    {
////                        Toast.makeText(this, "Account not found!!", Toast.LENGTH_SHORT).show()
////                    }
////                    else if (response.code() == 200)
////                    {
//                        session.createLoginSession(user_name, m_androidId.toString())
//                        //  session.save_details(user_age, user_grade)
//                        val intent = Intent(this, HomeActivity::class.java)
//                            .setAction(Intent.ACTION_VIEW)
//                            .setData(Uri.parse("success"))
//                        startActivity(intent)
//                        finish()
//
//                       // Log.d("user", response.code().toString())
//
//                   // }
//
////                    else
////                        Log.d("user", response.body().toString())
//                    // Toast.makeText(this, response.body().toString(), Toast.LENGTH_SHORT).show()
//                })
//                viewModel.login_user(user)
//
//                viewModel.errorMessage.observe(this) { response ->
//
//                    Log.d("userr", response.toString())
//                }
//            }
//            else
//            {
//                Toast.makeText(this, "please enter username", Toast.LENGTH_SHORT).show()
//            }
//        }
//        else
//        {
//            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show()
//        }
//        viewModel.login_user(user)
//    }

    private fun dologin() {
        user_name = binding.usernameEt.text.toString().trim()



        setlanguage()

        getDatafromOnlineDB(user_name)


        val user = Userz(user_name, "6-7", "germany", "German", "10", m_androidId.toString())

//        getUserInfoFromLocalDB(user_name)
//
//        if (isOnline(this)) {
//            if (user_name != "") { viewModel.login_user(user)
//                viewModel.myresponse.observe(this, Observer { response ->
//
//                    response.isSuccessful
//                    if (response.message() == "Not Found" ||response.errorBody()?.equals("404") == true)
//                    {
//                        Toast.makeText(this@Login, "Student not found", Toast.LENGTH_SHORT).show()
//                    }
//
//                    else if (response.body() != null)
//                    {
//                        if (response.body()!!.auth)
//                        {
//                            response.body()!!.token
//                            response.body()!!.auth
//                            session.createLoginSession(user_name, m_androidId.toString())
//                            //  session.save_details(user_age, user_grade)
//                            val intent = Intent(this@Login, HomeActivity::class.java)
//                                .setAction(Intent.ACTION_VIEW)
//                                .setData(Uri.parse("success"))
//                            startActivity(intent)
//                            finish()
//                        }
//
//                    }
//
//
//
//                    Log.d("userr", " response.body = ${response.body()}")
//                    Log.d("userr", "response.errorBody() = ${response.errorBody()}")
//                    Log.d("userr", "response.message() = ${response.message()}")
//
////                    if (response.isSuccessful) {
////                        Log.d("user", response.body().toString())
////                        Log.d("user", response.code().toString())
////                        session.createLoginSession(user_name, m_androidId.toString())
////                        //  session.save_details(user_age, user_grade)
////                        val intent = Intent(this@Login, HomeActivity::class.java)
////                            .setAction(Intent.ACTION_VIEW)
////                            .setData(Uri.parse("success"))
////                        startActivity(intent)
////                        finish()
////                    } else
////                        Log.d("user", response.body().toString())
////                    Toast.makeText(this@Login, response.body().toString(), Toast.LENGTH_SHORT).show()
//
//                })}
//
//            else {  Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()  }
//        }
    }

    private fun setlanguage() {
        if (language== "en")
        {
            materialLanguage = "English"


        }
        if (language== "de")
        {
            materialLanguage = "Deutsch"


        }
        if (language== "es")
        {
            materialLanguage = "español"


        }
        if (language== "el")
        {
            materialLanguage = "ελληνικά"


        }
    }

    private fun getDatafromOnlineDB(userName: String) {
        viewModel.userList.observe(this) {



            if (it.success)
            {
                checkUserDataInRoom(it)
            }
            else if (!it.success)
            {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Please create an account to continue", Toast.LENGTH_SHORT).show()
            }
        }


        viewModel.errorMessage.observe(this)
        {

            if (it == "500")
            {
                Toast.makeText(this, "Student not found", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Please create an account to continue", Toast.LENGTH_SHORT).show()

                viewModel.errorMessage.postValue(null)

            }


        }

        viewModel.getUserDatabyName(userName)
    }

    private fun checkUserDataInRoom(onlineUserData: OnlineUserData)
    {
        val dao: SeedsDao = SeedsDatabase.getInstance(this).seedsDao
        lifecycleScope.launch {

            if (dao.isUserExists(onlineUserData.data.name))
            {
                goToLoginActivity()
                Toast.makeText(this@Login, "User Exists", Toast.LENGTH_SHORT).show()
                Log.d("jaooo", language)
                Log.d("jaooo", "Material: $materialLanguage")

                session.save_details(onlineUserData.data.name,onlineUserData.data.age,onlineUserData.data.grade,materialLanguage)
            }
            else
            {
                checklang()
                dao.insertUser(User(
                    onlineUserData.data.name,onlineUserData.data.age,
                    onlineUserData.data.country,onlineUserData.data.grade,
                    onlineUserData.data.language, onlineUserData.data.dev_id,materialLanguage))
                session.save_materialLangPref(materialLanguage)
                session.save_details(onlineUserData.data.name,onlineUserData.data.age,onlineUserData.data.grade,materialLanguage)
                goToLoginActivity()

                Toast.makeText(this@Login, "User added to localDB", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this@Login, HomeActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(Uri.parse("success"))
        startActivity(intent)
        finish()
        session.createLoginSession(user_name, m_androidId.toString())
//               session.save_details(user_age, user_grade)
        session.save_materialLangPref(materialLanguage)

    }


    private fun getUserInfoFromLocalDB(user_name: String) {
        try {


            val dao: SeedsDao = SeedsDatabase.getInstance(this).seedsDao
            lifecycleScope.launch { dao.getUser(user_name)

                var user: List<User> = dao.getUser(user_name)

                if (user.isNotEmpty() )
                {
                    user.forEach {

                        session.save_details(it.username, it.age, it.grade, it.preferredmaterialLanguage)
                    }

                    Log.d("RoomDb", user.toString())

                }
                else
                {
                    Log.d("RoomDb", "Userlogin data not found")
                }

                Log.d("login", user.toString())

            }
        }catch (e: Exception)
        {
            Log.d("RoomDb", "Userlogin data not found and db crashed!!")
        }
    }


    private fun getDevID() {
        m_androidId = Secure.getString(contentResolver, ANDROID_ID)

        //Toast.makeText(this, m_androidId.toString(), Toast.LENGTH_SHORT).show()
        Log.d("DevID", m_androidId.toString())
    }
    private fun hideActionBar() {
        supportActionBar?.hide()
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
//        Toast.makeText(
//            this.requireContext(),
//            "Connection not available",
//            Toast.LENGTH_SHORT
//        ).show()
        return false
    }
}
