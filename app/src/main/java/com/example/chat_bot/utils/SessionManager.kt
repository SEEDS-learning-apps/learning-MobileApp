package com.example.chat_bot.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.example.chat_bot.Activities.Login
import com.example.chat_bot.Activities.MainActivity
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.data.tryy.QuestItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SessionManager {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var context: Context
    var PRIVATE_MODE: Int = 0

    constructor(context: Context) {

        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object {

        val PREF_NAME: String = "KotlinDemo"
        val IS_LOGIN: String = "isLogin"
        val KEY_NAME: String = "name"
        val KEY_AGE: String = "age"
        val KEY_devID: String = "devID"
        val KEY_InterfaceLang: String = "lang"
        val KEY_country: String = "country"
        val KEY_age: String = "age"
        val KEY_ObtainedScore: String = "KEY_ObtainedScore"
        val KEY_TotalScore: String = "KEY_TotalScore"
        val KEY_grade: String = "grade"
        val KEY_sub: String = "sub"
        val KEY_topic: String = "topic"
        val KEY_topicID: String = "KEY_topicID"
        val LIST_KEY = "list_key100"
        val savedQuiz_KEY = "dwnQuiz"
        val KEY_App_Mode = "appMode"
        val KEY_SWITCH = "KEY_SWITCH"
        val KEY_materialLang = "KEY_materialLang"
        val KEY_AccessCode = "KEY_AccessCode"
        val KEY_QUIZ = "KEY_QUIZ"
    }

//    fun createLoginSession(name: String, devID: String) {
//
//        editor.putBoolean(IS_LOGIN, true)
//        editor.putString(KEY_NAME, name)
//       // editor.putString(KEY_PASS, emai)
//        editor.putString(KEY_devID, devID)
//        editor.commit()
//    }

    fun quizDone(lang: String)
    {
        editor.putString(KEY_QUIZ, lang)
        editor.commit()
    }

    fun get_quizDone(): String
    {
        var ans: String = ""
        return pref.getString(KEY_QUIZ, ans).toString().also { ans = it }
    }


    fun save_details(username:String ,age: String, grade: String, materialLang: String)
    {
        editor.putString(KEY_NAME, username)
        editor.putString(KEY_age, age)
        editor.putString(KEY_grade, grade)
        editor.putString(KEY_materialLang, materialLang)
        editor.commit()
    }

    fun save_subject(sub: String)
    {
        editor.putString(KEY_sub, sub)
        editor.commit()
    }

    fun save_materialLangPref(lang: String)
    {
        editor.putString(KEY_materialLang, lang)
        editor.commit()
    }

    fun get_materialLangPref(): String
    {
        var lang: String = ""
        return pref.getString(KEY_materialLang, lang).toString().also { lang = it }
    }


    fun save_topicID(topic: String)
    {
        editor.putString(KEY_topicID, topic)
        editor.commit()
    }

    fun get_topicID(): String
    {
        var topic: String = ""
        return pref.getString(KEY_topicID, topic).toString().also { topic = it }
    }


    fun save_topic(topic: String)
    {
        editor.putString(KEY_topic, topic)
        editor.commit()
    }

    fun get_topic(): String
    {
        var topic: String = ""
        return pref.getString(KEY_topic, topic).toString().also { topic = it }
    }

    fun get_sub(): String
    {
        var sub: String = ""
        return pref.getString(KEY_sub, sub).toString().also { sub = it }
    }


    fun saveTOtalScore(t_score: String)
    {
        editor.putString(KEY_TotalScore, t_score)
        editor.commit()
    }

    fun getTOtalScore(): String {

        var t_score: String = ""
        return pref.getString(KEY_TotalScore, t_score).toString().also { t_score = it }
    }

    fun saveObtainedScore(o_score: String)
    {
        editor.putString(KEY_ObtainedScore, o_score)
        editor.commit()
    }

    fun getObtainedScore(): String {

        var o_score: String = ""
        return pref.getString(KEY_ObtainedScore, o_score).toString().also { o_score = it }
    }





    fun createLoginSession(name: String, devID: String) {

        editor.putBoolean(IS_LOGIN, true)
        editor.putString(KEY_NAME, name)
        // editor.putString(KEY_PASS, emai)
        editor.putString(KEY_devID, devID)
//        editor.putString(KEY_InterfaceLang, lang)
        editor.commit()
    }

    fun savePrivateMaterialsAccessCode(code: String)
    {
        editor.putString(KEY_AccessCode, code)
        editor.commit()
    }

    fun getPrivateMaterialsAccessCode(): String {

        var accessCode: String = ""
        return pref.getString(KEY_AccessCode, accessCode).toString().also { accessCode = it }
    }


    fun savelanguagePref(lang: String)
    {
        editor.putString(KEY_InterfaceLang, lang)
        editor.commit()
    }

    fun getlanguagePref(): String {

        var lang: String = ""
        return pref.getString(KEY_InterfaceLang, lang).toString().also { lang = it }
    }

    fun saveAppMode(mode: String)
    {
        editor.putString(KEY_App_Mode, mode)
        editor.commit()
    }

    fun getAppMode(): String {

        var mode: String = ""
        return pref.getString(KEY_App_Mode, mode).toString().also { mode = it }
    }

    fun saveSwitch(mode: Boolean)
    {
        editor.putBoolean(KEY_SWITCH, mode)
        editor.commit()
    }

    fun getSwitch(): Boolean {

        var mode: Boolean = false
        return pref.getBoolean(KEY_SWITCH, mode).also { mode = it }
    }



    fun checkLogin() {

        if (!this.isLoggedIn()) {
            var i: Intent = Intent(context, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(i)
        }
    }

    fun getUserDetails(): HashMap<String, String> {

        var user: Map<String, String> = HashMap()

        (user as HashMap).put(KEY_NAME, pref.getString(KEY_NAME, null).toString())
        user.put(KEY_AGE, pref.getString(KEY_AGE, null).toString())
        user.put(KEY_devID, pref.getString(KEY_devID, null).toString())
        user.put(KEY_grade, pref.getString(KEY_grade, null).toString())
        user.put(KEY_country, pref.getString(KEY_country, null).toString())
        user.put(KEY_materialLang, pref.getString(KEY_materialLang, null).toString())
        return user
    }

    fun logoutUser() {

        val i = Intent(context, Login::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(i)

        editor.remove(KEY_NAME)
        editor.clear()
        editor.commit()
        editor.apply()

    }

    fun isLoggedIn(): Boolean {

        return pref.getBoolean(IS_LOGIN, false)
    }

    fun writeListInPref(context: Context?, list: ArrayList<Exercise>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        val editor = pref.edit()
        editor.putString(LIST_KEY, jsonString)
        editor.apply()
    }

    fun saveQuiz(context: Context?, list: ArrayList<QuestItem>) {
        val gson = Gson()
        val jsonString = gson.toJson(list)
        val editor = pref.edit()
        editor.putString(savedQuiz_KEY, jsonString)
        editor.apply()
    }
    fun loadQuiz(context: Context?): ArrayList<QuestItem> {

        val emptyList = Gson().toJson(ArrayList<QuestItem>())
        val jsonString = pref.getString(savedQuiz_KEY, emptyList)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<QuestItem?>?>() {}.type
        return gson.fromJson<ArrayList<QuestItem>>(jsonString, type)
    }


    fun readListFromPref(context: Context?): List<Exercise?> {
        val emptyList = Gson().toJson(ArrayList<Exercise>())
        val jsonString = pref.getString(LIST_KEY, emptyList)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Exercise?>?>() {}.type
        return gson.fromJson<List<Exercise>>(jsonString, type)
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

    private val addToast = ArrayList<Toast>()

    private fun killAllToast() {
        for (t in addToast) {
            t?.cancel()
        }
        addToast.clear()
    }

}