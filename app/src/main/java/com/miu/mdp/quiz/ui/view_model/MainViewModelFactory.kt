package com.miu.mdp.quiz.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miu.mdp.quiz.QuizApplication
import com.miu.mdp.quiz.datasource.QuestionsRepository
import com.miu.mdp.quiz.datasource.UserRepository

class MainViewModelFactory(
    private val userRepository: UserRepository,
    private val questionRepository: QuestionsRepository,
    private val application: QuizApplication
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application, userRepository, questionRepository) as T
    }
}
