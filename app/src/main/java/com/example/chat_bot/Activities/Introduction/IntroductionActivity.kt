package com.example.chat_bot.Activities

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import android.widget.LinearLayout
import android.widget.TextView
import android.os.Bundle
import android.content.Intent
import android.text.Html
import android.view.View
import android.widget.Button
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.chat_bot.R

class IntoductionActivity : AppCompatActivity() {
    var mSLideViewPager: ViewPager? = null
    var mDotLayout: LinearLayout? = null

    lateinit var dots: Array<TextView?>
    var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        val skipbtn = findViewById<Button>(R.id.skipButton)

        skipbtn.setOnClickListener(View.OnClickListener {
            val i = Intent(this@IntoductionActivity, mainintroscreen::class.java)
            startActivity(i)
            finish()
        })
        mSLideViewPager = findViewById<View>(R.id.slideViewPager) as ViewPager
        mDotLayout = findViewById<View>(R.id.indicator_layout) as LinearLayout
        viewPagerAdapter = ViewPagerAdapter(this)
        mSLideViewPager!!.adapter = viewPagerAdapter
        setUpindicator(0)
        mSLideViewPager!!.addOnPageChangeListener(viewListener)
    }

    fun setUpindicator(position: Int) {
        dots = arrayOfNulls(4)
        mDotLayout!!.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226")
            dots[i]!!.textSize = 35f
            dots[i]!!
                .setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            mDotLayout!!.addView(dots[i])
        }
        dots[position]!!
            .setTextColor(resources.getColor(R.color.active, applicationContext.theme))
    }

    var viewListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            setUpindicator(position)
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    private fun getitem(i: Int): Int {
        return mSLideViewPager!!.currentItem + i
    }
}