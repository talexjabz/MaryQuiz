package com.miu.mdp.quiz.datasource.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miu.mdp.quiz.entity.QuestionAnswerHistory

@Dao
interface QuestionHistoryDao {
    @Insert(entity = QuestionAnswerHistory::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuestionHistory(questionResult: QuestionAnswerHistory)

    @Query("select * from QuestionAnswerHistory where userId = :userId")
    fun getQuestionHistory(userId: String): LiveData<List<QuestionAnswerHistory>>
}
