package com.example.chat_bot.Activities.HomePage

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat_bot.R
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.FragmentExerciseBinding
import com.example.chat_bot.ui.ExerciseHistoryAdapter
import com.example.chat_bot.utils.SessionManager


class ExerciseFragment : Fragment(), ExerciseHistoryAdapter.ExerciseDeleteListener {

    var exerciseList: ArrayList<Exercise> = arrayListOf()
    val adapter = ExerciseHistoryAdapter(this)
    private lateinit var binding: FragmentExerciseBinding
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val sharedPrefs: SharedPreferences =
            requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedPrefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            // if true then change the theme to dark mode
            requireContext().setTheme(R.style.DarkMode)
        } else {
            requireContext().setTheme(R.style.WhiteMode)
        }

        // Inflate the layout for this fragment
        binding = FragmentExerciseBinding.inflate(layoutInflater, container, false)
        session = SessionManager(context as Activity)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView()

    }

    private fun recyclerView() {

        val adapter = ExerciseHistoryAdapter(this@ExerciseFragment)

        binding.exRv.adapter = adapter
        binding.exRv.layoutManager = LinearLayoutManager(this.context)

        exerciseList = session.readListFromPref(context as Activity) as ArrayList<Exercise>
        if (exerciseList.isNotEmpty()) {
            manageViews()
            adapter.setExList(exerciseList)
        } else {
            manageViews(false)
        }
    }

    fun manageViews(hasExercise: Boolean = true) {
        binding.exRv.visibility = if (hasExercise) View.VISIBLE else View.GONE
        binding.noExercise.visibility = if (hasExercise) View.GONE else View.VISIBLE
    }

    override fun onExerciseDeleted() {
        val isEmpty = exerciseList.isEmpty()
        manageViews(!isEmpty)
    }

}
