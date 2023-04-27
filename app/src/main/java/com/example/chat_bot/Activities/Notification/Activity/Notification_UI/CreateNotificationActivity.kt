package com.example.chat_bot.Activities.activity

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chat_bot.Activities.Notification.Activity.Notification_fragment.NotificationFragment.Toast.displayFailureToast
import com.example.chat_bot.Activities.Notification.Activity.Notification_receiver.NotificationReciever
import com.example.chat_bot.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*


class CreateNotificationActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    lateinit var AM_PM: String
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedprefs: SharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)

        val switchIsTurnedOn = sharedprefs.getBoolean("DARK MODE", false)
        if (switchIsTurnedOn) {
            //if true then change app theme to dark mode
            layoutInflater.context.setTheme(R.style.DarkMode)
        } else {
            layoutInflater.context.setTheme(R.style.WhiteMode)
        }
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        setContentView(R.layout.notification_create)

        addDayChips()

        val backbtn = findViewById<ImageView>(R.id.Backbutton_Create_notification)

        backbtn.setOnClickListener{
            onBackPressed()
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }

        val btn_choose_time = findViewById<Button>(R.id.btn_choose_time)
        val btn_set_alarm = findViewById<Button>(R.id.btn_set_alarm)
        val timeTV = findViewById<TextView>(R.id.timeTV)

        btn_choose_time.setOnClickListener {
            val calendar=Calendar.getInstance()
            TimePickerDialog(this, { _, hour, min ->
                selectedHour = hour
                selectedMin = min

                var hourString = selectedHour.toString()
                var minString =  selectedMin.toString()
                if ( selectedHour > 12) {
                    hourString = ( selectedHour - 12).toString()
                    AM_PM = "PM"
                } else {
                    AM_PM = "AM"
                }

                if (selectedMin < 10) {
                    minString = "0$selectedMin"
                }
                val formattedTime = "$hourString:$minString $AM_PM"
                timeTV.text=formattedTime
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show()


        }

        //our set alarm button from create alarm xml
        btn_set_alarm.setOnClickListener {
            if(TextUtils.isEmpty(timeTV.text)){
                displayFailureToast(this,"please select a time")
                return@setOnClickListener
            }

            sendDataToAlarmFragment()

        }


    }

    private fun sendDataToAlarmFragment() {
        val timeTV = findViewById<TextView>(R.id.timeTV)
        val timeText = timeTV.text.toString()
        val builder = StringBuilder()
        val alarmIsOn = true
        var repeatdays: String? = null

        when (selectedDays.size) {
            0 -> {
                repeatdays = "Alarm"
            }
            1 -> selectedDays.forEachIndexed { _, day ->
                repeatdays = "Alarm, $day"
            }
            in 2..6 -> {
                selectedDays.forEachIndexed { _, days ->
                    val formatted = days.substring(6, 9)
                    builder.append("$formatted ")
                    repeatdays = builder.toString()
                }
            }
            else -> repeatdays = "Every day"
        }

        val intent = Intent()
        intent.putExtra(ALARM_TIME, timeText)
        intent.putExtra(ALARM_REPEAT_DAYS, repeatdays)
        intent.putExtra(ALARM_IsON, alarmIsOn)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }



    companion object {
        var selectedDays = mutableListOf<String>()
        private var selectedHour = 0
        private var selectedMin = 0

        fun startAlarm(alarmId:Int,context: Context) {
            selectedDays.forEachIndexed { _, day ->

                val indexOfDay = days.indexOf(day) + 1

                val calendar = Calendar.getInstance().apply {
                    set(Calendar.DAY_OF_WEEK, indexOfDay)
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMin)
                }
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 7)
                }

                val intent = Intent(context, NotificationReciever::class.java)
                val pendingIntent =
                    PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    7 * 24 * 60 * 60 * 1000,
                    pendingIntent
                )
            }

            if (selectedDays.isEmpty()) {
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMin)
                }
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1)
                }

                val intent = Intent(context, NotificationReciever::class.java)
                intent.putExtra(ALARM_REPEAT_DAYS, selectedDays.toTypedArray())
                intent.putExtra("selectedDays", selectedDays.toTypedArray())
                val pendingIntent =
                    PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    7 * 24 * 60 * 60 * 1000,
                    pendingIntent
                )
            }

        }

        //cancel alarm function
        fun cancelAlarm(id: Int, context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NotificationReciever::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, id, intent,PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)
        }

        const val ALARM_TIME = "ALARM_TIME"
        const val ALARM_IsON = "ALARM_IsON"
        const val ALARM_REPEAT_DAYS = "ALARM_REPEAT_DAYS"

        //list of repeat days
        private val days by lazy {
            listOf(
                "Every Sunday",
                "Every Monday",
                "Every Tuesday",
                "Every Wednesday",
                "Every Thursday",
                "Every Friday",
                "Every Saturday"
            )
        }
    }



    private fun addDayChips() {
        val cg_days_chips = findViewById<ChipGroup>(R.id.cg_days_chips)
        days.forEach { day ->
            cg_days_chips.addChip {
                text = day
                tag = day
                isCheckable
                isClickable
                setOnCheckedChangeListener(this@CreateNotificationActivity)
            }
        }
    }

    private fun ChipGroup.addChip(chipInitializer: Chip.() -> Unit) {
        val dayChip =
            layoutInflater.inflate(R.layout.notification_day_chip, null).findViewById<Chip>(R.id.chip_day)
        dayChip.setChipBackgroundColorResource(R.color.light_blue_600)

        val chip = dayChip.apply {
            chipInitializer(this)
        }
        addView(chip)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            selectedDays.add(buttonView?.tag.toString())
        } else {
            selectedDays.apply {
                removeAt(indexOf(buttonView?.tag.toString()))
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this@CreateNotificationActivity, Notification_MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right)
        }
        return super.onKeyDown(keyCode, event)
    }


}