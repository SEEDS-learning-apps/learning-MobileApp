package com.example.chat_bot.Activities

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.example.chat_bot.R


private var currentVideoView: VideoView? = null
private var currentButton: Button? = null
private val DELAY_TIME: Long = 3000

class ViewPagerAdapter(var context: Context) : PagerAdapter() {
    var images = intArrayOf(
        R.drawable.app_logo,
        R.drawable.chatbot,
        R.drawable.quiz,
        R.drawable.exercise
    )
    var headings = intArrayOf(
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three,
        R.string.heading_fourth
    )
    var description = intArrayOf(
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three,
        R.string.desc_fourth
    )



    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View


        // inflate a different layout for the last page
        if (position == count - 1) {
            view = layoutInflater.inflate(R.layout.slider_layout_video, container, false)
            val videoView = view.findViewById<View>(R.id.intro_video) as VideoView
            val playButton = view.findViewById<View>(R.id.play_button) as Button

            val videoUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.intro)
            videoView.setVideoURI(videoUri)

            currentVideoView = videoView
            currentButton = playButton

            currentButton?.setOnClickListener {
                currentVideoView?.start()
                currentButton?.visibility = View.GONE
            }

        } else {
            view = layoutInflater.inflate(R.layout.slider_layout, container, false)
            val slidetitleimage = view.findViewById<View>(R.id.titleImage) as ImageView
            val slideHeading = view.findViewById<View>(R.id.texttitle) as TextView
            val slideDesciption = view.findViewById<View>(R.id.textdeccription) as TextView
            slidetitleimage.setImageResource(images[position])
            slideHeading.setText(headings[position])
            slideDesciption.setText(description[position])
        }

        container.addView(view)
        return view
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        // if there is a current video view and button
        currentVideoView?.let { videoView ->
            currentButton?.let { button ->
                // pause the video and show the button
                if (position != count - 1 && videoView.isPlaying) {
                    videoView.pause()
                    button.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }


}