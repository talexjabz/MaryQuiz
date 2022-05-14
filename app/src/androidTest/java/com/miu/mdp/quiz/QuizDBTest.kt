package com.miu.mdp.quiz

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miu.mdp.quiz.datasource.QuizDB
import com.miu.mdp.quiz.datasource.dao.QuestionDao
import com.miu.mdp.quiz.datasource.dao.ResultDao
import com.miu.mdp.quiz.datasource.dao.UserDao
import com.miu.mdp.quiz.datasource.repository.QuestionsRepoImpl
import com.miu.mdp.quiz.datasource.repository.QuestionsRepository
import com.miu.mdp.quiz.datasource.repository.UserRepoImpl
import com.miu.mdp.quiz.datasource.repository.UserRepository
import com.miu.mdp.quiz.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class QuizDBTest {

    lateinit var quizDB: QuizDB
    lateinit var userDao: UserDao
    lateinit var resultDao: ResultDao
    lateinit var questionDao: QuestionDao

    lateinit var userRepository: UserRepository
    lateinit var questionsRepository: QuestionsRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        quizDB = Room
            .inMemoryDatabaseBuilder(context, QuizDB::class.java)
            .build()
        userDao = quizDB.getUserDao()
        resultDao = quizDB.getResultDao()
        questionDao = quizDB.getQuestionDao()
        userRepository = UserRepoImpl(userDao)
        questionsRepository = QuestionsRepoImpl(questionDao, resultDao)
    }

    @After
    fun closeDB() {
        quizDB.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertUser_insertsCorrectData(): Unit = runBlocking {
        val user = User(
            id = 1,
            userId = "12223",
            name = "Student",
            email = "some@me.com"
        )
        userDao.insertUser(user)
        val getUser = userDao.getUser("12223").getOrWaitFor()
        assert(getUser == user) {
            println("$user is not equal to $getUser")
        }

    }

    @Test
    fun insertResults_insertsCorrectUserResults(): Unit = runBlocking {
        val result = Result(
            userId = "1222",
            correct = 3,
            wrong = 1,
            date = Date()
        )
        val result2 = Result(
            id = 2,
            userId = "1222",
            correct = 5,
            wrong = 0,
            date = Date()
        )

        resultDao.insertResult(result)
        resultDao.insertResult(result2)

        val resultsList = resultDao.getResults(result.userId).getOrWaitFor()
        assert(resultsList.size == 2) {
            println("size is not ${resultsList.size}")
        }
        assert(resultsList[1].correct == result2.correct) {
            println("${resultsList[1].correct} is not ${result2.correct}")
        }
    }

    @Test
    fun test_insertQuestions_insertsCorrectQuestions() = runBlocking {
        createAndSaveQuestions()
        val resultsList = questionDao.getAllQuestions().getOrWaitFor()
        assert(resultsList.size == 2)
        assert(
            resultsList[0].radioAnswer == RadioAnswer(
                id = 1,
                listOf("Study of living things", "Study of Siri"),
                0
            )
        )
    }

    @Test
    fun test_userRepoWorks(): Unit = runBlocking {
        val userId = userRepository.register("ali", "ziwa")
        assert(userId.isNotEmpty())

        val currentUser = userRepository.login(userId).getOrWaitFor()
        assert(currentUser.userId == userId)

        assert(questionsRepository.getQuestions().getOrWaitFor().isEmpty())

        questionsRepository.generateQuestions(Dispatchers.Unconfined)
        val questions = questionsRepository.getQuestions().getOrWaitFor()
        assert(questions.size == 4)

        val resultsMap = listOf(0 to true, 1 to false, 2 to false, 3 to true).toMap()
        val result =
            questionsRepository.computeResults(userId, resultsMap, Dispatchers.Unconfined)
                .getOrWaitFor()
        val expectedResult = Result(
            userId = userId,
            correct = 2,
            wrong = 2,
            date = Date()
        )

        assert(result.correct == expectedResult.correct && result.wrong == expectedResult.wrong)
    }

    private suspend fun createAndSaveQuestions() {
        val question1 = Question(
            1,
            "What is science",
            "radio",
            radioAnswer = RadioAnswer(
                id = 1,
                listOf("Study of living things", "Study of Siri"),
                0
            )
        )
        val question2 = Question(
            2,
            "Nowhere man was created in which year",
            "text",
            textAnswer = TextAnswer(
                1,
                "I don't remember"
            )
        )
        questionDao.insertQuestion(question1, question2)
    }
}
