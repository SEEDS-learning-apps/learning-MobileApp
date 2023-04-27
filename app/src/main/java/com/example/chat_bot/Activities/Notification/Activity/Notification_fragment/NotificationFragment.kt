package com.example.chat_bot.Activities.Notification.Activity.Notification_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.Notification.Activity.Notification_fragment.NotificationFragment.Toast.displayFailureToast
import com.example.chat_bot.Activities.Notification.Activity.Notification_fragment.NotificationFragment.Toast.displaySuccessToast
import com.example.chat_bot.Activities.activity.CreateNotificationActivity
import com.example.chat_bot.R
import com.example.chat_bot.Activities.Notification.Activity.NotificationAdapter
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications
import com.example.chat_bot.Activities.Notification.Activity.Notification_UI.NotificationViewModel
import com.example.chat_bot.Activities.Notification.Activity.Notification_UI.NotificationViewModelFactory
import com.muddzdev.styleabletoastlibrary.StyleableToast
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

@Suppress("DEPRECATION")
class NotificationFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory: NotificationViewModelFactory by instance()
    private var viewModel: NotificationViewModel? = null
    private var adapter: NotificationAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPrefs: SharedPreferences = requireContext().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val switchIsTurnedOn = sharedPrefs.getBoolean("DARK MODE", false)

        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.Notification_DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.Notification_WhiteMode)
        }

        //this tells the fragment hey, we've got a menu item
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.notification_fragment, container, false)

        viewModel = ViewModelProvider(this, factory).get(NotificationViewModel::class.java)

        adapter = NotificationAdapter(viewModel!!, listOf())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter



        viewModel!!.getAllAlarms().observe(viewLifecycleOwner, Observer {
            //this checks if the recyclerview is empty
            if (it.isEmpty()) {
                view.findViewById<LinearLayout>(R.id.emptyRecView).visibility = View.VISIBLE
            } else {
                view.findViewById<LinearLayout>(R.id.emptyRecView).visibility = View.GONE
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
                    CreateNotificationActivity.cancelAlarm(deletedAlarm.id, requireContext())
                    //set its alarm to false
                    deletedAlarm.AlarmIsEnabled = false
                    //notifies the recyclerview that an item was removed
                    adapter!!.notifyItemRemoved(adapterPosition)

                    displaySuccessToast(requireContext(), "Alarm deleted")
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
                            Objects.requireNonNull<FragmentActivity>(
                                activity
                            ), R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.ic_delete)
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
        return view

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_CANCELED && data != null) {
            // get intent data from create alarm activity
            if (requestCode == CREATE_ALARM_REQUEST && resultCode == Activity.RESULT_OK) {
                val time = data.getStringExtra(CreateNotificationActivity.ALARM_TIME)
                val repeatDay = data.getStringExtra(CreateNotificationActivity.ALARM_REPEAT_DAYS)
                val alarmIsActive = data.getBooleanExtra(CreateNotificationActivity.ALARM_IsON, true)

                val alarm = Notifications(time!!, repeatDay!!, alarmIsActive)
                //insert the alarm into database using our viewmodel instance
                viewModel!!.insert(alarm)

                displaySuccessToast(requireContext(), "alarm created successfully")
            } else {
                displayFailureToast(requireContext(), "an error occurred")
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("InflateParams")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.create_new_alarm) {
            val intent = Intent(context, CreateNotificationActivity::class.java)
            startActivityForResult(intent, CREATE_ALARM_REQUEST)

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStart() {
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.Notification)

        super.onStart()
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



