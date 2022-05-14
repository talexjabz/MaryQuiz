package com.miu.mdp.quiz.datasource.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miu.mdp.quiz.datasource.dao.QuestionDao
import com.miu.mdp.quiz.datasource.dao.ResultDao
import com.miu.mdp.quiz.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.coroutines.CoroutineContext

class QuestionsRepoImpl(
    private val questionDao: QuestionDao,
    private val resultDao: ResultDao
) : QuestionsRepository {

    override fun generateQuestions(dispatcher: CoroutineContext) {
        val question1 = Question(
            question = "Which is not a valid kotlin data type?",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "Boolean", "Character", "Load", "Int"
                ),
                correct = 2
            )
        )

        val question2 = Question(
            question = "What are the correct ways to declare variables in kotlin?",
            type = "check",
            checkAnswer = CheckAnswer(
                options = listOf(
                    "var a = \"name is student\"",
                    "val b = 2",
                    "vax 5 = 12",
                    "const s = char"
                ),
                correct = listOf(0, 1)
            )
        )

        val question3 = Question(
            question = "What are data classes?",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "Class that holds data and provides typical functions",
                    "Class used to save data to share preferences",
                    "A companion object that stores data",
                    "None of the above"
                ),
                correct = 0
            )
        )

        val question4 = Question(
            question = "Write the keyword used to define a function in kotlin",
            type = "text",
            textAnswer = TextAnswer(
                correct = "fun"
            )
        )

        CoroutineScope(dispatcher).launch {
            questionDao.deleteAllQuestions()
            questionDao.insertQuestion(question1, question2, question3, question4)
        }
    }

    override fun getQuestions(): LiveData<List<Question>> {
        return questionDao.getAllQuestions()
    }

    override fun computeResults(
        userId: String, map: Map<Int, Boolean>,
        dispatcher: CoroutineContext
    ): LiveData<Result> {
        var correct = 0
        var wrong = 0

        for (answers in map.values) {
            if (!answers) wrong++
            if (answers) correct++
        }

        val result = Result(
            userId = userId,
            correct = correct,
            wrong = wrong,
            date = Date()
        )
        CoroutineScope(dispatcher).launch {
            resultDao.insertResult(result)
        }
        return MutableLiveData(result)
    }

    override fun getResult(userId: String): LiveData<List<Result>> {
        return resultDao.getResults(userId)
    }

}
