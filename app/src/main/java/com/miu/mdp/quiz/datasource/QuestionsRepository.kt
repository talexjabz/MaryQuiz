package com.miu.mdp.quiz.datasource

import androidx.lifecycle.LiveData
import com.miu.mdp.quiz.domain.*
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface QuestionsRepository {
    fun generateQuestions(dispatcher: CoroutineContext = Dispatchers.Main)
    fun getQuestions(): LiveData<List<Question>>
    fun computeResults(
        userId: String,
        map: Map<Int, Boolean>,
        dispatcher: CoroutineContext = Dispatchers.Main
    ): LiveData<Result>
    suspend fun saveQuestionAnswerHistory(
        userId: String,
        questionHistory: Set<QuestionAnswerHistory>
    )

    fun getQuestionAnswerHistory(
        userId: String
    ): LiveData<List<QuestionAnswerHistory>>

    fun getResult(userId: String): LiveData<List<Result>>
}

