package com.example.chat_bot.Activities.acivity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.AlarmAdapter
import com.example.chat_bot.Activities.acivity.AlarmActivity.Toast.displaySuccessToast
import com.example.chat_bot.R
import com.example.chat_bot.Room.Entities.Alarms
import com.example.chat_bot.ui.AlarmViewModel
import com.example.chat_bot.ui.AlarmViewModelFactory
import com.muddzdev.styleabletoastlibrary.StyleableToast
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

@Suppress("DEPRECATION")
class AlarmActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: AlarmViewModelFactory by instance()
    private var viewModel: AlarmViewModel? = null
    private var adapter: AlarmAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_alarm)


        viewModel = ViewModelProvider(this, factory).get(AlarmViewModel::class.java)

        adapter = AlarmAdapter(viewModel!!, listOf())

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val emptyRecView = this.findViewById<RecyclerView>(R.id.emptyRecView)

        viewModel!!.getAllAlarms().observe(this, Observer {
            //this checks if the recyclerview is empty
            if (it.isEmpty()) {
                emptyRecView.visibility = View.VISIBLE
            } else {
                emptyRecView.visibility = View.GONE
            }
            adapter!!.alarmList = it
            adapter!!.notifyDataSetChanged()
        })


        //swipe delete function
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                if (direction == ItemTouchHelper.LEFT) {
                    val adapterPosition = viewHolder.adapterPosition
                    //get item adapter position
                    val deletedAlarm = adapter!!.getAlarmAt(adapterPosition)
                    //delete it from the view model
                    viewModel!!.delete(deletedAlarm)
                    //cancel its alarm
                    CreateAlarmActivity.cancelAlarm(deletedAlarm.id, applicationContext)
                    //set its alarm to false
                    deletedAlarm.AlarmIsEnabled = false
                    //notifies the recyclerview that an item was removed
                    adapter!!.notifyItemRemoved(adapterPosition)

                    displaySuccessToast(applicationContext, "Alarm deleted")
                }
            }

            //swipe delete function continues
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            this@AlarmActivity, R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete_black_24dp)
                    .addSwipeLeftLabel("delete")
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }).attachToRecyclerView(recyclerView)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null && requestCode == CREATE_ALARM_REQUEST) {
            val time = data.getStringExtra(CreateAlarmActivity.ALARM_TIME)
            val repeatDay = data.getStringExtra(CreateAlarmActivity.ALARM_REPEAT_DAYS)
            val alarmIsActive = data.getBooleanExtra(CreateAlarmActivity.ALARM_IsON, true)

            val alarm = Alarms(time!!, repeatDay!!, alarmIsActive)
            viewModel?.insert(alarm)

            Toast.displaySuccessToast(this, "alarm created successfully")
        } else {
            Toast.displayFailureToast(this, "an error occurred")
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu_items, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_new_alarm) {
            val intent = Intent(this, CreateAlarmActivity::class.java)
            startActivityForResult(intent, CREATE_ALARM_REQUEST)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        super.onStart()
        supportActionBar?.title = getString(R.string.Alarms)
    }

    // toast object
    object Toast {
        fun displaySuccessToast(context: Context, message: String) {
            StyleableToast.makeText(context, message, R.style.myToast).show()
        }

        fun displayFailureToast(context: Context, message: String) {
            StyleableToast.makeText(context, message, R.style.myToast1).show()
        }
    }

    companion object {
        const val CREATE_ALARM_REQUEST = 1
    }
}





