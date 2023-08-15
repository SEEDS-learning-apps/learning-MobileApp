package com.example.chat_bot.utils


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.chat_bot.data.Message
import java.util.*


class DB (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION){

        companion object{
             val DATABASE_NAME = "Chatbot.db"
            val DATABASE_VERSION = 1
             const val MESSAGES_TABLE_NAME = "messages"
             const val MESSAGES_COLUMN_ID = "id"
             const val MESSAGES_COLUMN_DESCRIPTION = "description"
             const val MESSAGES_COLUMN_CREATED_AT = "created_at"
             const val MESSAGES_COLUMN_SENDER = "message_sender"
             const val MESSAGES_COLUMN_MESSAGE_TYPE = "message_type"
            var messagesList = mutableListOf<Message>()
            var msgBtn: List<com.example.chat_bot.Rasa.rasaMsg.Button> = arrayListOf()
        }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_MESSAGES_TABLE = (" CREATE TABLE IF NOT EXISTS " + MESSAGES_TABLE_NAME + "("
                + MESSAGES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + MESSAGES_COLUMN_DESCRIPTION + " TEXT,"
                + MESSAGES_COLUMN_MESSAGE_TYPE + " TEXT," + MESSAGES_COLUMN_SENDER + " TEXT," + MESSAGES_COLUMN_CREATED_AT + " TEXT " +  ")")
        db?.execSQL(CREATE_MESSAGES_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun insertMessage(message: Message): Long {
        val db = this.writableDatabase
        val messageValues = ContentValues()
        messageValues.put(MESSAGES_COLUMN_DESCRIPTION, message.message)
        messageValues.put(MESSAGES_COLUMN_MESSAGE_TYPE, message.msgId)
        messageValues.put(MESSAGES_COLUMN_CREATED_AT, message.time)
        messageValues.put(MESSAGES_COLUMN_SENDER, message.username)

        // Inserting Row
        val success = db.insert(MESSAGES_TABLE_NAME, null, messageValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    @SuppressLint("Range")
    fun getMessages(): MutableList<Message>  {
        val db = this.readableDatabase
        val cursor = db.rawQuery("select * from messages", null)
        Log.d("in the db", cursor.toString())
        cursor.moveToFirst()
      //      ORDER BY $MESSAGES_COLUMN_ID
        while (!cursor.isAfterLast) {
            val messageType = cursor.getString(cursor.getColumnIndex(MESSAGES_COLUMN_MESSAGE_TYPE))
            val TimeStamp = cursor.getString(cursor.getColumnIndex(MESSAGES_COLUMN_CREATED_AT))
            val description = cursor.getString(cursor.getColumnIndex(MESSAGES_COLUMN_DESCRIPTION))
            val message = Message(description, messageType, TimeStamp, false,"", msgBtn,"sender")

            messagesList.add(message)
            cursor.moveToNext()
        }
        return messagesList
    }

}