package com.example.chat_bot.Room.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms_items")
class Alarms(
    @ColumnInfo(name = "alarm_time")
    var time:String,
        )
{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}


