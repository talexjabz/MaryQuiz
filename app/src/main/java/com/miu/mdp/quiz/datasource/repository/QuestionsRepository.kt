package com.miu.mdp.quiz.datasource.repository

import androidx.lifecycle.LiveData
import com.miu.mdp.quiz.entity.*
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

    fun getResult(userId: String): LiveData<List<Result>>
}

