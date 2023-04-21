package com.example.chat_bot.Activities.Notification.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.Notification.Activity.Notification_UI.NotificationViewModel
import com.example.chat_bot.Activities.Notification.Activity.Notification_data.entities.Notifications
import com.example.chat_bot.Activities.activity.CreateNotificationActivity
import com.example.chat_bot.R
import com.example.chat_bot.databinding.NotificationItemsBinding

class NotificationAdapter(
    private val notificationViewModel: NotificationViewModel,
    var alarmList: List<Notifications>
) :
    RecyclerView.Adapter<NotificationAdapter.AlarmViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_items, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int {

        return alarmList.size
    }

    fun getAlarmAt(position: Int): Notifications {
        return alarmList[position]
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val currentAlarm = alarmList[position]
        val binding = NotificationItemsBinding.bind(holder.itemView)

        binding.timeTv.text = currentAlarm.time
        binding.daysTv.text = currentAlarm.repeatDays

        //this basically checks the state of the switch
        binding.isActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentAlarm.AlarmIsEnabled = true

                CreateNotificationActivity.selectedDays.clear()

                CreateNotificationActivity.startAlarm(currentAlarm.id, holder.itemView.context)
                notificationViewModel.update(currentAlarm)
            } else {
                currentAlarm.AlarmIsEnabled = false
                CreateNotificationActivity.cancelAlarm(currentAlarm.id,holder.itemView.context)
                notificationViewModel.update(currentAlarm)
            }
        }


        binding.isActive.isChecked = currentAlarm.AlarmIsEnabled
    }


    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
