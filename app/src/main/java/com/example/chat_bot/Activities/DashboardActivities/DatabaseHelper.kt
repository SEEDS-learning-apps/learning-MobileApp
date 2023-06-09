package com.example.chat_bot.Activities.DashboardActivities

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "subject_counts.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "subject_counts"
        private const val COLUMN_SUBJECT = "subject"
        private const val COLUMN_COUNT = "count"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_SUBJECT TEXT PRIMARY KEY, $COLUMN_COUNT INTEGER)"
        db.execSQL(createTableQuery)
        initializeSubjectCounts(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades if needed
    }

    private fun initializeSubjectCounts(db: SQLiteDatabase) {
        val subjects = listOf(
            "Business",
            "Chemistry",
            "Psychology",
            "Technology",
            "Language",
            "Math",
            "History",
            "Biology",
            "Physics",
            "Humanities",
            "Art and Design"
        )

        subjects.forEach { subject ->
            val values = ContentValues()
            values.put(COLUMN_SUBJECT, subject)
            values.put(COLUMN_COUNT, 0)
            db.insert(TABLE_NAME, null, values)
        }
    }


    fun increaseCount(subject: String) {
        val db = writableDatabase
        val query = "UPDATE $TABLE_NAME SET $COLUMN_COUNT = $COLUMN_COUNT + 1 WHERE $COLUMN_SUBJECT = ?"
        db.execSQL(query, arrayOf(subject))
        db.close()
    }

    fun getCount(subject: String): Int {
        val db = readableDatabase
        val query = "SELECT $COLUMN_COUNT FROM $TABLE_NAME WHERE $COLUMN_SUBJECT = ?"
        val cursor = db.rawQuery(query, arrayOf(subject))
        val count = if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(COLUMN_COUNT)
            if (columnIndex != -1) {
                cursor.getInt(columnIndex)
            } else {
                0
            }
        } else {
            0
        }
        cursor.close()
        db.close()
        return count
    }

}
