package com.miu.mdp.quiz.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.miu.mdp.quiz.domain.User

@Dao
interface UserDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from User where email=:email")
    suspend fun getUser(email: String): User?
}

