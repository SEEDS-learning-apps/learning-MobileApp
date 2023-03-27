package com.example.chat_bot.Ac

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chat_bot.Activities.acivity.Help
import com.example.chat_bot.Activities.acivity.downloadQuizActivity
import com.example.chat_bot.Activities.acivity.quiz_home
import com.example.chat_bot.R
import com.example.chat_bot.databinding.FragmentDashboardBinding
import com.example.chat_bot.utils.AppMode
import com.example.chat_bot.utils.Language
import com.example.chat_bot.utils.SessionManager
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var bind: FragmentDashboardBinding
    lateinit var session: SessionManager
    var appmode: AppMode? = null
    lateinit var lang: Language
    var m_androidId: String? = null
    private val USER = "M-f8f2e818-808f-"
    lateinit var userename: String
    var pref_material_language: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        session = SessionManager(this.requireContext())
        var user = session.getUserDetails()

        userename = user.get("name").toString()


        bind = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        showUserProfile()

        bind.signoutTv.setOnClickListener { handleClicks() }


        return bind.root


    }


    @SuppressLint("HardwareIds")
    private fun getDevID() {
        m_androidId =
            Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        m_androidId = "$m_androidId/" + UUID.randomUUID().toString()
        //Toast.makeText(this, m_androidId.toString(), Toast.LENGTH_SHORT).show()
        Log.d("dev_id", "Device ID: $m_androidId")
    }


    @SuppressLint("SetTextI18n")
    private fun showUserProfile() {

        val user = session.getUserDetails()
        val name = user["name"]

        Log.d("DashboardFragment", "name: $name")

        bind.profileUsername.text = getString(R.string.Hello) + " " + name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else {
                it.toString()
            }
        } + ","

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.ProfileCardview.setOnClickListener {
            alertbox_profile()
        }

        bind.DownloadCardview.setOnClickListener {
            showDownload()
        }

        bind.AccessCodeHere.setOnClickListener {
            alertbox_accessCode()
        }

        bind.SettingsCardview.setOnClickListener {
            val intent =
                Intent(this.context, com.example.chat_bot.Activities.acivity.Settings::class.java)
            startActivity(intent)
        }

        bind.HelpCardview.setOnClickListener {
            val intent = Intent(this.context, Help::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
        }


//        bind.complexTV.setOnClickListener {  loadHome()}




    }

    private fun showDownload() {
        val intent = Intent(context, downloadQuizActivity::class.java).apply {

        }
        startActivity(intent)
    }

    private fun loadHome() {
        val intent = Intent(context, quiz_home::class.java).apply {


        }
        startActivity(intent)
    }


    private fun handleClicks() {
        session.logoutUser()
        (context as Activity).finish()

    }

    fun alertbox_profile() {

        val builder = AlertDialog.Builder(context, R.style.PauseDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.profile_card, null)

        val button = view.findViewById<Button>(R.id.profile_closebtn)
        builder.setView(view)
        button.setOnClickListener {
            builder.dismiss()
        }

        val user = session.getUserDetails()


        val age = user["age"]
        println(age)
        val name = user["name"]
        val grade = user["grade"]

        val materialLang = user["KEY_materialLang"]

        Log.d("DashboardFragment", "age: $age")
        Log.d("DashboardFragment", "name: $name")
        Log.d("DashboardFragment", "grade: $grade")
        Log.d("DashboardFragment", "materialLang: $materialLang")

        val profilemateriallang = view.findViewById<TextView>(R.id.profilematerialLang)
        val profileuserage = view.findViewById<TextView>(R.id.profileUserage)
        val profilegrade = view.findViewById<TextView>(R.id.profilegrade)
        val profileusername = view.findViewById<TextView>(R.id.profilecardUsername)


        profileusername.text = name?.capitalize()
        profilemateriallang.text = materialLang
        profileuserage.text = age
        profilegrade.text = grade



        builder.setCanceledOnTouchOutside(false)
        builder.show()

    }

    fun alertbox_accessCode() {

        val builder = AlertDialog.Builder(context, R.style.PauseDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.aceess_code_dialog, null)

        val et = view.findViewById<EditText>(R.id.code_et)

        val button = view.findViewById<Button>(R.id.code_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {
//            val intent = Intent(context, HomeActivity::class.java)
//            startActivity(intent)
            val code = et.text.trim()
            if (code.isNotEmpty()) {
                Log.d("SettingsFragment", "Access Code: ${code.toString()}")
                Toast.makeText(context, "access code saved!!!", Toast.LENGTH_SHORT).show()
                session.savePrivateMaterialsAccessCode(code.toString())
            }

            builder.dismiss()

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }


//    fun onBackpressed(){
//        val intent = Intent(context, HomeActivity::class.java)
//        startActivity(intent)
//    }

//    fun onBackPressed() {
//        val count = childFragmentManager.backStackEntryCount
//        if (count == 0) {
//            super.onBackPressed()
//            //additional code
//        } else {
//            childFragmentManager.popBackStack()
//        }
//    }

//    fun onKeyDown(keyCode: Int, event: KeyEvent?) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            val navHostFragment = fragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//            val navController = navHostFragment.navController
//            navController.navigate(R.id.chatFragment)
//        }
//    }

//   override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            val intent = Intent(requireContext(), HomeActivity::class.java)
//            startActivity(intent)
//            return true
//        }
//        return true
//    }
//
//}

//    private fun Fragment.onBackPressed() {
//        val count = childFragmentManager.backStackEntryCount
//        if (count == 0) {
//            return onBackPressed()
//        //additional code
//        } else {
//            childFragmentManager.popBackStack()
//        }
//    }

}




