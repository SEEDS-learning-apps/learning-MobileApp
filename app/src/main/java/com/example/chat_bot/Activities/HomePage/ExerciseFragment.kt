package com.example.chat_bot.Activities.HomePage

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.chat_bot.data.Exercise
import com.example.chat_bot.databinding.FragmentExerciseBinding
import com.example.chat_bot.ui.ExerciseHistoryAdapter
import com.example.chat_bot.utils.SessionManager


class ExerciseFragment : Fragment() {

    var exerciseList: ArrayList<Exercise> = arrayListOf()
    val adapter = ExerciseHistoryAdapter(this)
    private val TAG = "EXFragment"

    private lateinit var binding: FragmentExerciseBinding
    lateinit var session: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        binding = FragmentExerciseBinding.inflate(layoutInflater, container, false)
        session = SessionManager(context as Activity)
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        recyclerView()
        // hideActionBar()

    }

    private fun recyclerView() {

        binding.exRv.adapter = adapter
        binding.exRv.layoutManager = LinearLayoutManager(this.context)

        //   if (!exerciseList.isNullOrEmpty())
        // {
        exerciseList = session.readListFromPref(context as Activity) as ArrayList<Exercise>
        // }
        // var list: ArrayList<Exercise> = arrayListOf()


        //Log.d("listaaa", list.toString())
        if (exerciseList.isNotEmpty()) {

            adapter.setExList(exerciseList)
            adapter.notifyDataSetChanged()
            adapter.notifyItemInserted(exerciseList.size)
        } else
            false

    }


}
