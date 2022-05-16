package com.miu.mdp.quiz.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.miu.mdp.quiz.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.util.*
import kotlin.coroutines.CoroutineContext

class QuestionsRepoImpl(
    private val questionDao: QuestionDao,
    private val resultDao: ResultDao,
    private val questionHistoryDao: QuestionHistoryDao
) : QuestionsRepository {

    override fun generateQuestions(dispatcher: CoroutineContext) {
        val question1 = Question(
            question = "What is the only number that does not have its Roman numeral? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "2", "0", "1", "7"
                ),
                correct = 1
            )
        )

        val question2 = Question(
            question = "Which flat picture may also be shown in three dimensions? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf("Hologram", "Nanogram", "Hectogram", "polygram"),
                correct = 0
            )
        )

        val question3 = Question(
            question = "What comes after a trillion? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "Quantrillion",
                    "Quadrillion",
                    "100 trillion",
                    "Zillion"
                ),
                correct = 1
            )
        )

        val question4 = Question(
            question = "Icosahedrons have how many equal sides? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = "Twenty"
            )
        )

        val question5 = Question(
            question = "In 1882, Whiz Ferdinand Von Lindemann discovered which Mathematical Symbol? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = "Pi"
            )
        )
        val question6 = Question(
            question = "What is the name of an angle of more than 90 degrees but less than 180 degrees? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = "Obtuse angle"
            )
        )
        val question7 = Question(
            question = "How many digits does the value of Pi have? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "100 million",
                    "4",
                    "48",
                    "Infinity"
                ),
                correct = 3
            )
        )
        val question8 = Question(
            question = "When is Pi Day celebrated? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "14 March",
                    "8 November",
                    "28 February",
                    "4 July"
                ),
                correct = 0
            )
        )

        val question9 = Question(
            question = "The use of Arabic Numerals is invented in what country?",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "China",
                    "India",
                    "Uganda",
                    "Greece"
                ),
                correct = 1
            )
        )

        val question10 = Question(
            question = "Who created the equal sign? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = " Robert Recorde"
            )
        )

        val question11 = Question(
            question = "How many sides does an “enneadecagon” have? ",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "19",
                    "12",
                    "54",
                    "0"
                ),
                correct = 0
            )
        )
        val question12 = Question(
            question = "What does the term ‘crore’ mean?",
            type = "radio",
            radioAnswer = RadioAnswer(
                options = listOf(
                    "Two hundred",
                    "Ten Million",
                    "Four thousand",
                    "Forty size thousand"
                ),
                correct = 1
            )
        )
        val question13 = Question(
            question = "What is the term used for the perimeter of a circle? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = "Circumference"
            )
        )

        val question14 = Question(
            question = "What is the shape of the mathematical symbol lemniscate? ",
            type = "text",
            textAnswer = TextAnswer(
                correct = "Infinity"
            )
        )

        CoroutineScope(dispatcher).launch {
            questionDao.deleteAllQuestions()
            questionDao.insertQuestion(
                question1, question2, question3, question4,
                question5, question6, question7, question8,
                question9, question10, question11, question12,
                question13, question14
            )
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

    override suspend fun saveQuestionAnswerHistory(
        userId: String,
        questionHistory: Set<QuestionAnswerHistory>
    ) {
        questionHistory.forEach {
            it.userId = userId
            questionHistoryDao.saveQuestionHistory(it)
        }
    }

    override fun getQuestionAnswerHistory(userId: String): LiveData<List<QuestionAnswerHistory>> {
        return questionHistoryDao.getQuestionHistory(userId)
    }
}
