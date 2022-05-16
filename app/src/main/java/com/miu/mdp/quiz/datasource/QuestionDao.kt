package com.miu.mdp.quiz.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miu.mdp.quiz.domain.Question

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @Transaction
    suspend fun insertQuestion(vararg questions: Question)

    @Query("select * from Question")
    fun getAllQuestions(): LiveData<List<Question>>

    @Query("delete from Question")
    suspend fun deleteAllQuestions()
}
