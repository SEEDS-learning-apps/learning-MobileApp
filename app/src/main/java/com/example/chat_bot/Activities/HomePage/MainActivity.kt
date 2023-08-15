package com.example.chat_bot.Activities.HomePage

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityMainBinding
import com.example.chat_bot.utils.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var session: SessionManager
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null


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

        session = SessionManager(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        tabLayout = binding.tabslayout
        viewPager2 = binding.viewpager
        viewPager2.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> applicationContext.resources.getString(R.string.chat)
                1 -> applicationContext.resources.getString(R.string.mainpage_History_heading)
                2 -> applicationContext.resources.getString(R.string.mainpage_Dashboard_heading)
                else -> throw Resources.NotFoundException("Position Not Found!!")
            }
        }.attach()

        val fragmentIndex = intent.getIntExtra("FRAGMENT_TO_SHOW", -1)
        if (fragmentIndex >= 0) {
            // Show the desired fragment
            viewPager2.setCurrentItem(fragmentIndex, false)
        }

    }

    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast?.cancel()
                finish()
                super.onBackPressed()
                return
            } else {
                backToast = Toast.makeText(baseContext, getString(R.string.press_back), Toast.LENGTH_SHORT)
                backToast?.show()
            }
            backPressedTime = System.currentTimeMillis()
        } else {
            viewPager2.setCurrentItem(0, true)

        }

    }

}







