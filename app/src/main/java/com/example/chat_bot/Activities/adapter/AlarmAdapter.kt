package com.example.chat_bot.Activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.activity.CreateNotificationActivity
import com.example.chat_bot.R
import com.example.chat_bot.Room.Entities.Alarms
import com.example.chat_bot.ui.AlarmViewModel
import com.google.android.material.switchmaterial.SwitchMaterial


class AlarmAdapter(
    private val alarmViewModel: AlarmViewModel,
    var alarmList: List<Alarms>
) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_items, parent, false)
        return AlarmViewHolder(view)

    }

    override fun getItemCount(): Int {

        return alarmList.size
    }

    fun getAlarmAt(position: Int): Alarms {
        return alarmList[position]
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {

        val currentAlarm = alarmList[position]
        val timeTextView = holder.itemView.findViewById<TextView>(R.id.time_tv)
        timeTextView.text = currentAlarm.time

        val days_tv = holder.itemView.findViewById<TextView>(R.id.days_tv)
        days_tv.text = currentAlarm.repeatDays

        val isActive: SwitchMaterial = holder.itemView.findViewById(R.id.isActive)
        //this basically checks the state of the switch
        isActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentAlarm.AlarmIsEnabled = true

                CreateNotificationActivity.selectedDays.clear()

                CreateNotificationActivity.startAlarm(currentAlarm.id, holder.itemView.context)
                alarmViewModel.update(currentAlarm)
            } else {
                currentAlarm.AlarmIsEnabled = false
                CreateNotificationActivity.cancelAlarm(currentAlarm.id,holder.itemView.context)
                alarmViewModel.update(currentAlarm)
            }
        }


        isActive.isChecked = currentAlarm.AlarmIsEnabled
    }


    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
