package com.example.chat_bot.Activities.HomePage
import android.app.Activity
import android.content.res.Resources
import android.net.http.SslCertificate.restoreState
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.chat_bot.R
import com.example.chat_bot.databinding.ActivityHomeBinding
import com.example.chat_bot.utils.SessionManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yariksoffice.lingver.Lingver


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        session = SessionManager(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val topic_namez = intent.getSerializableExtra("Total score").toString()
        topic_namez
//       val  topic_name = intent.getSerializableExtra("Total score").toString()
//        Toast.makeText(applicationContext, topic_name, Toast.LENGTH_SHORT).show()
//
//        if (topic_name != "null")
//        {
//            val chatFragment = ChatFragment()
//            chatFragment.jugnu(topic_name)
//        }


        tabLayout = binding.tabslayout
        viewPager2 = binding.viewpager
        viewPager2.adapter = PagerAdapter(this)
        TabLayoutMediator(tabLayout, viewPager2){ tab, index ->
            tab.text = when(index){
                0 -> {"Chat"}
                1 -> applicationContext.resources.getString(R.string.mainpage_ex_heading)
                2 -> applicationContext.resources.getString(R.string.mainpage_settings_heading)
                else ->{throw Resources.NotFoundException("Position Not Found!!")}
            }
            //checksavedlangpref()

        }.attach()
    }


    private fun checksavedlangpref(){

        if (session.getlanguagePref() == "")
        {
            val lang = Lingver.getInstance().getLanguage()
            session.savelanguagePref(lang)
           // Toast.makeText(this, lang, Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this, session.getlanguagePref(), Toast.LENGTH_SHORT).show()
    }
}