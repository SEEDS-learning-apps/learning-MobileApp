package com.example.chat_bot.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.chat_bot.Activities.Login
import com.example.chat_bot.Activities.QuizActivity
import com.example.chat_bot.Room.Coverters.ButtonConverter
import com.example.chat_bot.Room.Coverters.MaterialListConverter
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.data.Message
import com.example.chat_bot.data.R_Message
import com.example.chat_bot.data.User
import com.example.chat_bot.data.tryy.DowloadedQuiz
import com.example.chat_bot.data.tryy.QuestItem
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [
        User::class,
        R_Message:: class,
        QuestItem::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(value = arrayOf(ButtonConverter::class, MaterialListConverter::class))

abstract class SeedsDatabase: RoomDatabase() {

    abstract val seedsDao: SeedsDao

    companion object{

        @Volatile //prevents race conditions -> any changes made here are visible to all threads
        private var INSTANCE: SeedsDatabase?= null

        fun getInstance(context: Context): SeedsDatabase{
            // synchronised block helps in the way that no other thread can access this block or to say database
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    SeedsDatabase::class.java,
                    "seeds_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }

    }
}