package com.example.chat_bot.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.chat_bot.Room.Coverters.ButtonConverter
import com.example.chat_bot.Room.Coverters.MaterialListConverter
import com.example.chat_bot.Room.Dao.SeedsDao
import com.example.chat_bot.Room.Entities.Alarms
import com.example.chat_bot.data.QuestItem
import com.example.chat_bot.data.R_Message
import com.example.chat_bot.data.User

@Database(
    entities = [
        User::class,
        R_Message::class,
        QuestItem::class,
        Alarms::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(value = [ButtonConverter::class, MaterialListConverter::class])
abstract class SeedsDatabase : RoomDatabase() {

    abstract val seedsDao: SeedsDao

    companion object {
        @Volatile
        private var INSTANCE: SeedsDatabase? = null

        fun getInstance(context: Context): SeedsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SeedsDatabase::class.java,
                    "seeds_db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Perform any initial setup or migration logic here for the first database creation
                        }

                        override fun onOpen(db: SupportSQLiteDatabase) {
                            super.onOpen(db)
                            // Perform any migration logic here when the database is opened
                            // Use SQL statements to modify the schema

                            // Example: Add a new column to an existing table
                            db.execSQL("ALTER TABLE tableName ADD COLUMN newColumnName TEXT NOT NULL DEFAULT ''")
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
