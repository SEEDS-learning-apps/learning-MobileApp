package com.example.chat_bot.Activities.HomePage

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chat_bot.Activities.DashboardActivities.Help
import com.example.chat_bot.Activities.DashboardActivities.LearningProgress
import com.example.chat_bot.Activities.DashboardActivities.downloadQuizActivity
import com.example.chat_bot.R
import com.example.chat_bot.databinding.FragmentDashboardBinding
import com.example.chat_bot.utils.SessionManager
import java.util.*


class DashboardFragment : Fragment() {

    private lateinit var bind: FragmentDashboardBinding
    lateinit var session: SessionManager
    lateinit var userename: String


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

        session = SessionManager(this.requireContext())
        var user = session.getUserDetails()

        userename = user.get("name").toString()


        bind = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        showUserProfile()

        bind.signoutTv.setOnClickListener { handleClicks() }


        return bind.root


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
                Intent(this.context, com.example.chat_bot.Activities.DashboardActivities.Settings::class.java)
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        bind.HelpCardview.setOnClickListener {
            val intent = Intent(this.context, Help::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        bind.LearningprogressCardview.setOnClickListener {
            val intent = Intent(this.context, LearningProgress::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
            startActivity(intent)
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun showDownload() {
        val intent = Intent(context, downloadQuizActivity::class.java).apply {

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

}




