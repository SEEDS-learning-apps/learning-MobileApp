package com.example.chat_bot.Activities.HomePage

import android.content.res.Resources
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chat_bot.Ac.SettingsFragment

class PagerAdapter(fragmentAtctivity: FragmentActivity) : FragmentStateAdapter(fragmentAtctivity) {
    override fun getItemCount() = 3


    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {ChatFragment()}
            1 -> {ExerciseFragment()}
            2 -> { SettingsFragment()
            }
            else ->{throw Resources.NotFoundException("Position Not Found!!")}
        }

    }



}