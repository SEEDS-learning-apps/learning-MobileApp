package com.example.chat_bot.Ac

import com.example.chat_bot.Activities.HomePage.HomeActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.chat_bot.Activities.acivity.downloadQuizActivity
import com.example.chat_bot.Activities.acivity.quiz_home
import com.example.chat_bot.R
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.SeedsDatabase
import com.example.chat_bot.databinding.FragmentSettingsBinding
import com.example.chat_bot.utils.AppMode
import com.example.chat_bot.utils.Language
import com.example.chat_bot.utils.SessionManager
import com.yariksoffice.lingver.Lingver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class SettingsFragment : Fragment() {

    private lateinit var bind: FragmentSettingsBinding
    lateinit var session: SessionManager
    var appmode: AppMode? = null
    lateinit var lang:Language
    var m_androidId: String ?= null
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

       bind = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

       // bind.editUserDetails.setOnClickListener { EditUserDetails() }



        bind.materialLanguage.visibility = View.VISIBLE
        showUserProfile()


        bind.signoutTv.setOnClickListener{handleClicks()}

        if (session.getSwitch())
        {
            bind.switch1.isChecked = true
        }
        else if (!session.getSwitch())
        {
            bind.switch1.isChecked = false
        }



        bind.switch1.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked)
            {
                session.saveSwitch(true)
                session.saveAppMode("vanilla")


                Toast.makeText(this.requireContext(), "Vannilla Mode ON", Toast.LENGTH_SHORT).show()

            }

            else
            {
                session.saveSwitch(false)
                session.saveAppMode("rasa")


                Toast.makeText(this.requireContext(), "Rasa Mode ON", Toast.LENGTH_SHORT).show()
            }

        }

        return bind.root

    }

    @SuppressLint("HardwareIds")
    private fun getDevID() {
        m_androidId = Settings.Secure.getString(requireContext().contentResolver, Settings.Secure.ANDROID_ID)
        m_androidId =  "$m_androidId/" + UUID.randomUUID().toString()
        //Toast.makeText(this, m_androidId.toString(), Toast.LENGTH_SHORT).show()
        Log.d("dev_id", "Device ID: $m_androidId")
    }

    private fun EditUserDetails() {

        // Toast.makeText(context, "wow", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()

        val view = LayoutInflater.from(context).inflate(R.layout.edit_user_dialog, null)

        builder.setView(view)

        val age_EditText = view.findViewById<Button>(R.id.et_age)
        val grade_EditText = view.findViewById<Button>(R.id.et_grade)
        val materialLang_EditText= view.findViewById<Button>(R.id.et_material)

        var user = session.getUserDetails()


       var age =  age_EditText.text
        var grade =  grade_EditText.text
        var  materialLang =  materialLang_EditText.text
        var username = user.get("name")

        val btnDone = view.findViewById<Button>(R.id.btn_done)

       // val user_details = User(username.toString(), age.toString(), country = "germany",
          //  grade.toString(), "",  )

        btnDone.setOnClickListener {

          //  SaveToLocalDB(age,grade,materialLang,)

            (context as Activity).recreate()
            builder.dismiss()
        }
    }


    private fun showUserProfile() {

        val user = session.getUserDetails()



        val age = user["age"]
       println(age)
        val name = user["name"]
        val grade = user["grade"]

        val materialLang = user[ "KEY_materialLang"]

        Log.d("SettingsFragment", "age: $age")
        Log.d("SettingsFragment", "name: $name")
        Log.d("SettingsFragment", "grade: $grade")
        Log.d("SettingsFragment", "materialLang: $materialLang")
        bind.profilematerialLang.text = materialLang
        bind.profileUserage.text = age
        bind.profileUsername.text= name
        bind.profilegrade.text= grade

        bind.materialLanguage?.text = materialLang


    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        bind.accessCodeTv.setOnClickListener{
            alertbox_accessCode()
        }

        bind.materialLanguageTv.setOnClickListener { alertbox_materiallanguage()

            //UpdateMaterialLang()
        }

        bind.languageTv.setOnClickListener{
            alertbox_language()
        }
        bind.complexTV.setOnClickListener {  loadHome()}

        bind.contactTv.setOnClickListener { open_Contact_dialog() }

        bind.downloadTV.setOnClickListener { showDownload() }
      //  handle_clicks()
    }

    private fun open_Contact_dialog() {
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()

        val view = LayoutInflater.from(context).inflate(R.layout.contact_us_dialog, null)

        builder.setView(view)
        val btnclose = view.findViewById<Button>(R.id.close_contact_dialog)

        btnclose.setOnClickListener {

            builder.dismiss()
        }
        builder.show()
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

    fun alertbox_accessCode()
    {
       // Toast.makeText(context, "wow", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()
        val view = LayoutInflater.from(context).inflate(R.layout.aceess_code_dialog,null)

        val et = view.findViewById<EditText>(R.id.code_et)

        val  button = view.findViewById<Button>(R.id.code_return_to_chat)
        builder.setView(view)
        button.setOnClickListener {
            val intent = Intent(context,HomeActivity::class.java)
            val code = et.text.trim()
            if (code.isNotEmpty())
            {
                Log.d("SettingsFragment", "Access Code: ${code.toString()}")
                Toast.makeText(context, "access code saved!!!", Toast.LENGTH_SHORT).show()
                session.savePrivateMaterialsAccessCode(code.toString())
            }

            builder.dismiss()

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun alertbox_language()
    {

        // Toast.makeText(context, "wow", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()

        val view = LayoutInflater.from(context).inflate(R.layout.language_dialog,null)

        builder.setView(view)
        val btnGerman = view.findViewById<Button>(R.id.btn_german)

        btnGerman.text = "Deutsch"


        btnGerman.setOnClickListener { Lingver.getInstance().setLocale(this.requireContext(), "de")
            (context as Activity).recreate()
            builder.dismiss()
        }

        val btnSpanish = view.findViewById<Button>(R.id.btn_spanish)
        btnSpanish.text="Español"

        btnSpanish.setOnClickListener { Lingver.getInstance().setLocale(this.requireContext(), "es")
            (context as Activity).recreate()
            builder.dismiss()}

        val btnGreek = view.findViewById<Button>(R.id.btn_greek)
        btnGreek.text = "ελληνικά"


        btnGreek.setOnClickListener { Lingver.getInstance().setLocale(this.requireContext(), "el")
            (context as Activity).recreate()
            builder.dismiss()}

        val btnEnglish = view.findViewById<Button>(R.id.btn_english)

        btnEnglish.setOnClickListener { Lingver.getInstance().setLocale(this.requireContext(), "en")
            (context as Activity).recreate()
            builder.dismiss()}

        val  button = view.findViewById<Button>(R.id.code_return_to_chat)

        button.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context?.startActivity(intent)
            (context as Activity).finish()

        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
    }

    fun alertbox_materiallanguage()
    {
        // Toast.makeText(context, "wow", Toast.LENGTH_SHORT).show()
        val builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
            .create()

        val view = LayoutInflater.from(context).inflate(R.layout.material_language_dialog,null)

        builder.setView(view)
        val btnGerman = view.findViewById<Button>(R.id.btn_german)

        btnGerman.setOnClickListener {
            session.save_materialLangPref("German")
            pref_material_language ="German"
            UpdateMaterialLang(pref_material_language)
            (context as Activity).recreate()

            builder.dismiss()
        }

        val btnSpanish = view.findViewById<Button>(R.id.btn_spanish)
        btnSpanish.setOnClickListener {
             session.save_materialLangPref("Spanish")
            pref_material_language = "Spanish"
            UpdateMaterialLang(pref_material_language)
            (context as Activity).recreate()
           // CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()}

        val btnGreek = view.findViewById<Button>(R.id.btn_greek)
        btnGreek.setOnClickListener {
           session.save_materialLangPref("Greek").toString()
            pref_material_language =  "Greek"
            UpdateMaterialLang(pref_material_language)
            (context as Activity).recreate()
          //  CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()}

        val btnEnglish = view.findViewById<Button>(R.id.btn_english)
        btnEnglish.setOnClickListener {
            session.save_materialLangPref("English")
            pref_material_language = "English"
            UpdateMaterialLang(pref_material_language)
            (context as Activity).recreate()
          //  CoroutineScope(Dispatchers.IO).launch { UpdateMaterialLang() }
            builder.dismiss()}



        val  button = view.findViewById<Button>(R.id.code_return_to_chat)

        button.setOnClickListener {
            val intent = Intent(context,HomeActivity::class.java)
            context?.startActivity(intent)
            (context as Activity).finish()


        }
        builder.setCanceledOnTouchOutside(false)
        builder.show()
                         }

    private fun UpdateMaterialLang(language: String) {
        val dao: SeedsDao = SeedsDatabase.getInstance(context as Activity).seedsDao
        lifecycleScope.launch (Dispatchers.IO){

            dao.updatedMaterialLanguage(language, userename.toString())

        }
    }

}
