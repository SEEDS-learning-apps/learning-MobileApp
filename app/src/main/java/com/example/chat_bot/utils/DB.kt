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

//        // TODO Auto-generated method stub
//        db?.execSQL("CREATE TABLE IF NOT EXISTS "  + MESSAGES_TABLE_NAME+ "(" + MESSAGES_COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT ," + MESSAGES_COLUMN_DESCRIPTION + " TEXT," + MESSAGES_COLUMN_MESSAGE_TYPE + " TEXT," +
//                    MESSAGES_COLUMN_CREATED_AT + " TEXT"+ ")"

        //)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun insertMessage(message: Message): Long {
//        val dateFormat = SimpleDateFormat("EEE, MMM d, hh:mm a")
//        var unixTime: Long = 0
//        try {
//            val date = dateFormat.parse(message.getDate())
//            unixTime = date.time / 1000L
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        val unixTimeString = java.lang.Long.toString(unixTime)
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

//    //method to read data
//    fun viewMessages():MutableList<Message>{
//
//        val selectQuery = "SELECT * FROM  $MESSAGES_TABLE_NAME ORDER BY $MESSAGES_COLUMN_ID"
//        val db = this.readableDatabase
//        var cursor: Cursor? = null
//        try{
//            cursor = db.rawQuery(selectQuery, null)
//        }catch (e: SQLiteException) {
//            db.execSQL(selectQuery)
//            return ArrayList()
//        }
//        var userId: String
//        var userName: String
//        var userEmail: String
//        if (cursor.moveToFirst()) {
//            do {
//                userId = cursor.getInt(cursor.getColumnIndex("id"))
//                userName = cursor.getString(cursor.getColumnIndex("name"))
//                userEmail = cursor.getString(cursor.getColumnIndex("email"))
//                val emp= Message(userId = userId, userName = userName, userEmail = userEmail)
//                empList.add(emp)
//            } while (cursor.moveToNext())
//        }
//        return empList
//    }

//    fun insertMessage(msg: Message):Long {
//
//        val db = this.writableDatabase
//        val messageValues = ContentValues()
//        messageValues.put(MESSAGES_COLUMN_DESCRIPTION,
//        )
//        messageValues.put(
//            com.techcloud.chatbot.DBHelper.MESSAGES_COLUMN_MESSAGE_TYPE,
//            message.getMessageType()
//        )
//        messageValues.put(com.techcloud.chatbot.DBHelper.MESSAGES_COLUMN_CREATED_AT, unixTimeString)
//        messageValues.put(
//            com.techcloud.chatbot.DBHelper.MESSAGES_COLUMN_PREVIEW_IMAGE_URL,
//            message.getPreviewImageUrl()
//        )
//        messageValues.put(
//            com.techcloud.chatbot.DBHelper.MESSAGES_COLUMN_PREVIEW_VIDEO_URL,
//            message.getPreviewVideoUrl()
//        )
//        db.insert(com.techcloud.chatbot.DBHelper.MESSAGES_TABLE_NAME, null, messageValues)
//        return true
//    }

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

//            val dv = java.lang.Long.valueOf(unixTimeStamp) * 1000
//            val df = Date(dv)
           // val dateString = SimpleDateFormat("EEE, MMM d, hh:mm a").format(df)
            val message = Message(description, messageType, TimeStamp, false,"", msgBtn,"sender")

            messagesList.add(message)
            cursor.moveToNext()
        }
        //messagesList.removeAll { i->i.username != sender }
        return messagesList
    }



}