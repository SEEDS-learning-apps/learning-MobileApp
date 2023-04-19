package com.example.chat_bot.alarm_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat_bot.Activities.acivity.CreateAlarmActivity
import com.example.chat_bot.R
import com.example.chat_bot.alarm_data.entities.Alarms
import com.example.chat_bot.alarm_ui.AlarmViewModel
import com.example.chat_bot.databinding.AlarmItemsBinding

class AlarmAdapter(
    private val alarmViewModel: AlarmViewModel,
    var alarmList: List<Alarms>
) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_items, parent, false)
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
        val binding = AlarmItemsBinding.bind(holder.itemView)

        binding.timeTv.text = currentAlarm.time
        binding.daysTv.text = currentAlarm.repeatDays

        //this basically checks the state of the switch
        binding.isActive.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                currentAlarm.AlarmIsEnabled = true

                CreateAlarmActivity.selectedDays.clear()

                CreateAlarmActivity.startAlarm(currentAlarm.id, holder.itemView.context)
                alarmViewModel.update(currentAlarm)
            } else {
                currentAlarm.AlarmIsEnabled = false
                CreateAlarmActivity.cancelAlarm(currentAlarm.id,holder.itemView.context)
                alarmViewModel.update(currentAlarm)
            }
        }


        binding.isActive.isChecked = currentAlarm.AlarmIsEnabled
    }


    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
