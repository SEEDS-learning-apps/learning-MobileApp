package com.example.chat_bot.Activities

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.VideoView
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.example.chat_bot.R


private var currentVideoView: VideoView? = null
private var currentButton: Button? = null

class ViewPagerAdapter(var context: Context) : PagerAdapter() {

    override fun getCount(): Int {
               return 4
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View

        if (position == 0) {
            // inflate the layout that includes the LottieAnimationView
            view = layoutInflater.inflate(R.layout.slider_layout_page1, container, false)
            val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.lottie_animation_page1)

            // load the animation from the JSON file
            lottieAnimationView.setAnimation(R.raw.e_learning)

        } else if (position == 1) {
            // inflate the layout that includes the LottieAnimationView
            view = layoutInflater.inflate(R.layout.slider_layout_page2, container, false)
            val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.lottie_animation_page2)

            // load the animation from the JSON file
            lottieAnimationView.setAnimation(R.raw.chatbot)

        } else if (position == 2) {
            // inflate the layout that includes the LottieAnimationView
            view = layoutInflater.inflate(R.layout.slider_layout_page3, container, false)
            val lottieAnimationView = view.findViewById<LottieAnimationView>(R.id.lottie_animation_page3)

            // load the animation from the JSON file
            lottieAnimationView.setAnimation(R.raw.statistics)

        } else if (position == count - 1) {
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

            videoView.setOnCompletionListener {
                playButton.visibility = View.VISIBLE
                videoView.seekTo(0)
            }

        } else {
            return false
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