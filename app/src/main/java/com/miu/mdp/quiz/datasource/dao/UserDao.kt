package com.miu.mdp.quiz.datasource.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.miu.mdp.quiz.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from User where userId=:userId")
    suspend fun getUser(userId: String): User?
}

