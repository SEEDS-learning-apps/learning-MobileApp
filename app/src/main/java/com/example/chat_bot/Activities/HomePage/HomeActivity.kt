package com.example.chat_bot.Activities.HomePage
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.chat_bot.Ac.DashboardFragment
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityHomeBinding
import com.example.chat_bot.utils.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var session: SessionManager
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        session = SessionManager(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = binding.tabslayout
        viewPager2 = binding.viewpager
        viewPager2.adapter = PagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager2) { tab, index ->
            tab.text = when (index) {
                0 -> "Chat"
                1 -> applicationContext.resources.getString(R.string.mainpage_ex_heading)
                2 -> applicationContext.resources.getString(R.string.mainpage_Dashboard_heading)
                else -> throw Resources.NotFoundException("Position Not Found!!")
            }
        }.attach()



    }

    override fun onBackPressed() {
        if (viewPager2.currentItem == 0) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast?.cancel()
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







