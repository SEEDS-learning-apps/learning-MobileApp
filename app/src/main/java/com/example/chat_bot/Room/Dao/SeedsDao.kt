package com.example.chat_bot.Room.Dao

import androidx.room.*
import com.example.chat_bot.Room.Relations.UserAndMessage
import com.example.chat_bot.data.R_Message
import com.example.chat_bot.data.User
import com.example.chat_bot.data.tryy.QuestItem

@Dao
interface SeedsDao{

    @Transaction //Avoids Multithreading issues
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: R_Message)

    @Transaction //Avoids Multithreading issues
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Transaction //Avoids Multithreading issues
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): List<User>

    @Transaction //Avoids Multithreading issues
    @Query("SELECT EXISTS(SELECT * FROM user WHERE username = :username)")
    suspend fun isUserExists(username: String): Boolean

    @Transaction //Avoids Multithreading issues
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMaterial(questItem: ArrayList<QuestItem>)

    @Transaction //Avoids Multithreading issues
    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getMessagesAndUserwithUsername(username: String): List<UserAndMessage>

    @Query("UPDATE user SET preferredmaterialLanguage = :preferredmaterialLanguage WHERE username =:username")
    fun updatedMaterialLanguage(preferredmaterialLanguage: String?, username: String?)

    @Transaction //Avoids Multithreading issues
    @Query("select * from QuestItem where _id = :id")
    suspend fun checkMaterialsWithID(id: String): List<QuestItem>

    @Transaction //Avoids Multithreading issues
    @Query("select * from QuestItem where username = :username")
    suspend fun getMaterialsWithUsername(username: String): List<QuestItem>

    @Transaction
    @Delete
    suspend fun deleteMaterial(questItem: QuestItem): Int
}
