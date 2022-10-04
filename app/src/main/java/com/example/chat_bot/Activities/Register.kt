package com.example.chat_bot.Activities

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.Activities.HomePage.HomeActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.data.User
import com.example.chat_bot.data.Userz
import com.example.chat_bot.databinding.ActivityRegisterBinding
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSRepository
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModel
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSViewModelFact
import com.example.chat_bot.networking.Retrofit.Seeds_api.api.SEEDSApi
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
    //private val USER = "M-" + UUID.randomUUID().toString()
    private var USER = "M-f8f2e818-808f-"
    var user_name:String = ""
    var user_age: String = ""
    var user_country: String = ""
    var materialLang:String = ""
    private lateinit var user_language: String
    var user_grade: String = ""
    var m_androidId: String ?= null
    private lateinit var language: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
       // Lingver.init(this.application)

        language = ""

        USER += getDevID()



        hideActionBar()
        //isOnline(this)
        viewModel = ViewModelProvider(this, SEEDSViewModelFact(SEEDSRepository(retrofitService))).get(SEEDSViewModel::class.java)
        session = SessionManager(applicationContext)

        user_language = ""
        checkLogin()

        binding.btnReg.setOnClickListener{
            //doAuthentication()
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







//            spinner.onItemSelectedListener = object :
//                AdapterView.OnItemSelectedListener {
//                override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View, position: Int, id: Long,
//                ) {
//                    Toast.makeText(this@Register, languages[position], Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onNothingSelected(parent: AdapterView<*>) {
//                    // write code to perform some action
//                }
//            }
        // }

//        binding.langBtn.setOnClickListener{
//            lang()
//            Toast.makeText(this, "cick", Toast.LENGTH_SHORT).show()
//        }

        //            val mutListIterator = grade.listIterator()
//
//            while(mutListIterator.hasNext()){
//                print(mutListIterator.next())
//            }



/* Name of your Custom JSON list */
//        val resourceId =
//            resources.getIdentifier("country_avail", "raw", applicationContext.packageName)
//
//        val countryPicker: CountryPickerDialog = CountryPickerDialog(this,
//            { country, flagResId ->
//
//
//
//            /* Get Country Name: country.getCountryName(context); */
//
//                /* Call countryPicker.dismiss(); to prevent memory leaks */
//            } /* Set to false if you want to disable Dial Code in the results and true if you want to show it
//         Set to zero if you don't have a custom JSON list of countries in your raw file otherwise use
//         resourceId for your customly available countries */, false, 0)
//
//        countryPicker.show()


      //  setlang()




    }

    private fun gotoLoginScreen() {
        val intent = Intent(this, Login::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setData(Uri.parse("success"))
        startActivity(intent)
        finish()
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (currentFocus != null) {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        }
//        return super.dispatchTouchEvent(ev)
//    }

    private fun setMateriallang() {

        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)
        val countries = resources.getStringArray(R.array.Countries)

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
//                Toast.makeText(this@Register,
//                    user_language,
//                    Toast.LENGTH_SHORT).show()
              //  lang(languages[position],adapter)





            }

        }
    }


    private fun setlang() {
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)
        val countries = resources.getStringArray(R.array.Countries)

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
//                Toast.makeText(this@Register,
//                    user_language,
//                    Toast.LENGTH_SHORT).show()
                lang(languages[position],adapter)





            }

        }
    }

    override fun onResume() {
        super.onResume()

        init_views()
        setlang()
        setMateriallang()

    }

    fun init_views() {



        // access the language spinner
//        val country_spinner = binding.countryBtn
//        if (country_spinner != null) {
//            val adapter = ArrayAdapter(this,
//                R.layout.lang_dropdown, countries)
//            country_spinner.setAdapter(adapter)
//
//            country_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    countries[position],
//                    Toast.LENGTH_SHORT).show()
//
//                user_country = countries[position]
////                lang(countries[position])
//
//
//            }
//        }



//        // access the items of the list
//        val garde = resources.getStringArray(R.array.grades)
//
//        // access the language spinner
//        val grade_spinner = binding.classBtn
//        if (grade_spinner != null) {
//            val adapter = ArrayAdapter(this,
//                R.layout.age_drop_down, gradesList)
//            grade_spinner.setAdapter(adapter)
//
//            grade_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    age.get(position),
//                    Toast.LENGTH_SHORT).show()
//                //age_setter(age.get(position))
//
//
//            }
    }


    private fun setgrade(it: MutableList<String>) {
//        // access the items of the list
//        val garde = resources.getStringArray(R.array.grades)
        grade_spinner = binding.classBtn
        // access the language spinner
        if (grade_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.age_drop_down, it)
            grade_spinner.setAdapter(adapter)
            adapter.notifyDataSetChanged()

            grade_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                //Toast.makeText(this@Register,
//                    it[position].toString(),
//                    Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                user_grade = it[position]
                //age_setter(age.get(position))


            }
    }


    }

    private fun setAGEGROUPS(it: MutableList<String>) {
        // access the items of the list
        val garde = resources.getStringArray(R.array.grades)

        // access the language spinner
        val age_spinner = binding.ageBtn
        if (age_spinner != null) {
            val adapter = ArrayAdapter(this,
                R.layout.age_drop_down, it)
            age_spinner.setAdapter(adapter)
            adapter.notifyDataSetChanged()


            age_spinner.onItemClickListener = OnItemClickListener { parent, view, position, id ->
//                Toast.makeText(this@Register,
//                    it[position].toString(),
//                    Toast.LENGTH_SHORT).show()
                user_age = it[position]
                adapter.notifyDataSetChanged()
                //age_setter(age.get(position))


            }
        }

    }
    private fun materiallang(lang: String, adapter: ArrayAdapter<String>){


        if(lang == "German")
        {


            // session.savelanguagePref(Lingver.getInstance().setLocale(this, "de").toString())
            recreate()
            adapter.notifyDataSetChanged()


        }
        else if(lang == "Spanish")
        {


            // session.savelanguagePref(Lingver.getInstance().setLocale(this, "es").toString())
            recreate()
            adapter.notifyDataSetChanged()

        }
        else if(lang == "English")
        {

            recreate()
            adapter.notifyDataSetChanged()

        }

        else if(lang == "Greek")
        {


            recreate()
            adapter.notifyDataSetChanged()


        }
        else

        adapter.notifyDataSetChanged()

//        else if(lang == "Greek")
//        {
//            Lingver.getInstance().setLocale(this, "el")
//            recreate()
        //              session.savelanguagePref(lang)
//        }


    }



    private fun lang(lang: String, adapter: ArrayAdapter<String>){


        if(lang == "German")
        {

            Lingver.getInstance().setLocale(this, "de")
           // session.savelanguagePref(Lingver.getInstance().setLocale(this, "de").toString())
            recreate()
            adapter.notifyDataSetChanged()


        }
        else if(lang == "Spanish")
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

        else if(lang == "Greek")
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

//        else if(lang == "Greek")
//        {
//            Lingver.getInstance().setLocale(this, "el")
//            recreate()
    //              session.savelanguagePref(lang)
//        }


    }

    private fun hideActionBar() {
        supportActionBar?.hide()
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
           // Toast.makeText(this, session.getlanguagePref(), Toast.LENGTH_SHORT).show()
            Log.d("details",session.getUserDetails().toString())
            val intent = Intent(this, HomeActivity::class.java)
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

    private fun doAuthentication() {
        if (binding.usernameEt.text == null || user_name == "")
        {
            Toast.makeText(applicationContext, "Username required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.ageBtn.text == null || user_age == "")
        {
            Toast.makeText(applicationContext, "age required", Toast.LENGTH_SHORT).show()
        }
         else if (binding.classBtn.text == null || user_grade == "")
        {
            Toast.makeText(applicationContext, "grade required", Toast.LENGTH_SHORT).show()
        }
        else if (binding.langBtnn.text == null || user_language == "")
        {
            Toast.makeText(applicationContext, "language required", Toast.LENGTH_SHORT).show()
        }
        else
        {
           // dologin()
        }

    }
    private fun dologin() {
        if (isOnline(applicationContext))
        {
            user_name = binding.usernameEt.text.toString().trim()
            user_age = binding.ageBtn.text.toString().trim()
            user_language = binding.langBtnn.text.toString().trim()
            user_grade = binding.classBtn.text.toString().trim()
            materialLang = binding.materiallangBtn.text.toString().trim()

//            getDevID()
//            var deviceID = "M-f8f2e818-80-$m_androidId"
          //  Log.d("tara", deviceID)

            user_country = "Germany"

            user_grade


            val user = Userz(user_name, user_age,  user_country,  user_grade, user_language, m_androidId.toString())

            val user1 = User(user_name, user_age,  user_country,  user_grade, user_language, m_androidId.toString(), materialLang)


            if (user_name.isBlank() || user_age.isBlank() || user_grade.isBlank() ||

                user_language.isBlank() || materialLang.isBlank())
            {
                Toast.makeText(applicationContext, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
            else
            {

                viewModel.create_user(user)
                viewModel.myresponse.observe(this, Observer {  response->


                    if (response.code() == 400 )
                    {
                        Toast.makeText(this, "That user already exists!", Toast.LENGTH_SHORT).show()
                        //viewModel.myresponse.postValue(null)
                    }
                    if (response.code() == 500 )
                    {
                        Toast.makeText(this, "Please fill correct data!!", Toast.LENGTH_SHORT).show()
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
//            if (response.isSuccessful)
//            {
//                Log.d("user", response.body().toString())
//                Log.d("user", response.code().toString())
//                session.createLoginSession(user_name, m_androidId.toString())
//                session.save_details(user_age, user_grade)
//                val intent = Intent(this, Login::class.java)
//                    .setAction(Intent.ACTION_VIEW)
//                    .setData(Uri.parse("success"))
//                startActivity(intent)
//                finish()
//            }
//            else
//                Log.d("user", response.body().toString())
//            Toast.makeText(this, response.message().toString(), Toast.LENGTH_SHORT).show()
                })


//        if(user_name == "alpha")
//        {
//            session.createLoginSession(user_name, m_androidId.toString())
//            session.save_details(user_age, user_grade, "germany")
//            val intent = Intent(this, HomeActivity::class.java)
//                .setAction(Intent.ACTION_VIEW)
//                .setData(Uri.parse("success"))
//            startActivity(intent)
//            finish()
//        }
//        else
//        {
//            Toast.makeText(this, "wrong credentials", Toast.LENGTH_SHORT).show()
//        }


            }
        }

        else
        {
            checkInternet()
        }
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