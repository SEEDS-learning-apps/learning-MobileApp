package com.example.chat_bot.Room.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.chat_bot.Room.Entities.Alarms
import com.example.chat_bot.Room.Relations.UserAndMessage
import com.example.chat_bot.data.R_Message
import com.example.chat_bot.data.User
import com.example.chat_bot.data.QuestItem

@Dao
interface SeedsDao {

    // Count functions
    @Query("SELECT count FROM subject_counts WHERE subject = :subject")
    suspend fun getCount(subject: String): Int

    @Query("UPDATE subject_counts SET count = count + 1 WHERE subject = :subject")
    suspend fun incrementCount(subject: String)

    @Query("UPDATE subject_counts SET count = count - 1 WHERE subject = :subject")
    suspend fun decrementCount(subject: String)

    // Alarm functions
    @Insert
    suspend fun insert(alarm: Alarms)

    @Update
    suspend fun update(alarm: Alarms)

    @Delete
    suspend fun delete(alarm: Alarms)

    @Query("DELETE FROM alarms_items")
    suspend fun deleteAllAlarms()

    @Query("SELECT * FROM alarms_items")
    fun getAllAlarms(): LiveData<List<Alarms>>

    // Seeds functions
    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: R_Message)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Transaction
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): List<User>

    @Transaction
    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    suspend fun isUserExists(username: String): Boolean

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMaterial(questItem: ArrayList<QuestItem>)

    @Transaction
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getMessagesAndUserwithUsername(username: String): List<UserAndMessage>

    @Query("UPDATE user SET preferredmaterialLanguage = :preferredmaterialLanguage WHERE username =:username")
    fun updatedMaterialLanguage(preferredmaterialLanguage: String?, username: String?)

    @Transaction
    @Query("select * from QuestItem where _id = :id")
    suspend fun checkMaterialsWithID(id: String): List<QuestItem>

    @Transaction
    @Query("select * from QuestItem where username = :username")
    suspend fun getMaterialsWithUsername(username: String): List<QuestItem>

    @Transaction
    @Delete
    suspend fun deleteMaterial(questItem: QuestItem): Int
}
