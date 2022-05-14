package com.miu.mdp.quiz.datasource.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miu.mdp.quiz.entity.Result

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: Result)

    @Query("select * from Result where userId=:userId order by date DESC")
    fun getResults(userId: String): LiveData<List<Result>>
}
