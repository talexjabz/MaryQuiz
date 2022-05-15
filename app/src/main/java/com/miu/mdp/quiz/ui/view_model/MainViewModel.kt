package com.miu.mdp.quiz.ui.view_model

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.miu.mdp.quiz.QuizApplication
import com.miu.mdp.quiz.datasource.repository.QuestionsRepository
import com.miu.mdp.quiz.datasource.repository.UserRepository
import com.miu.mdp.quiz.entity.QuestionAnswerHistory
import com.miu.mdp.quiz.entity.Result
import com.miu.mdp.quiz.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val IS_LOGGED_IN = "is_logged_in"

class MainViewModel(
    context: QuizApplication,
    private val userRepository: UserRepository,
    private val questionsRepository: QuestionsRepository
) : AndroidViewModel(context) {

    var currentUser: User? = null
        private set

    private val currentQuestionScore = mutableMapOf<Int, Boolean>()
    private val questionAnswerHistory = mutableSetOf<QuestionAnswerHistory>()

    private val sharedPrefs = context.getSharedPreferences("quiz_prefs", Context.MODE_PRIVATE)

    private var _selectedNext = MutableLiveData<Int>()
    val selectedNext: LiveData<Int>
        get() = _selectedNext

    private var _onQnAnswered = MutableLiveData<Boolean>()
    val onQnAnswered: LiveData<Boolean>
        get() = _onQnAnswered

    /**
     * Login,
     * register,
     * Store session data,
     * Check session data
     * Get results
     * Maintain current page and result.
     */

    init {
        questionsRepository.generateQuestions()
    }

    fun login(userId: String): LiveData<Boolean> {
        val isLoggedIn = MutableLiveData<Boolean>()
        CoroutineScope(Dispatchers.Default).launch {
            val user = userRepository.login(userId)
            if (user != null) {
                isLoggedIn.postValue(true)
                currentUser = user
                saveLoggedIn(user)
            } else {
                isLoggedIn.postValue(false)
            }
        }
        return isLoggedIn
    }

    private fun saveLoggedIn(currentUser: User?) {
        val stringUser = Gson().toJson(currentUser)
        sharedPrefs.edit()
            .putString(IS_LOGGED_IN, stringUser)
            .apply()
    }

    fun checkLoggedIn(): Boolean {
        currentUser = Gson().fromJson(sharedPrefs.getString(IS_LOGGED_IN, null), User::class.java)
        return currentUser != null
    }

    fun logout() {
        saveLoggedIn(null)
    }

    suspend fun register(name: String, email: String) = userRepository.register(name, email)

    fun getAllQuestions() = questionsRepository.getQuestions()
    fun updateScore(position: Int, isCorrect: Boolean, questionResult: QuestionAnswerHistory) {
        currentQuestionScore[position] = isCorrect
        if (questionAnswerHistory.contains(questionResult)) {
            questionAnswerHistory.remove(questionResult)
        }
        questionAnswerHistory.add(questionResult)
    }

    fun clearQuestions() {
        currentQuestionScore.clear()
    }

    fun didSelectNext(position: Int) {
        _selectedNext.postValue(position)
    }

    fun didAnswer(ans: Boolean) {
        _onQnAnswered.postValue(ans)
    }

    fun didCompleteQuiz(): LiveData<Result> {

        CoroutineScope(Dispatchers.Default).launch {
            questionsRepository.saveQuestionAnswerHistory(currentUser?.userId!!, questionAnswerHistory)
        }
        return questionsRepository.computeResults(currentUser?.userId!!, currentQuestionScore)
    }

    fun getUserPerformance() = currentUser?.userId?.let { questionsRepository.getResult(it) }

    fun getQuestionAnswerHistory() = currentUser?.userId?.let {
        questionsRepository.getQuestionAnswerHistory(it)
    }


}

