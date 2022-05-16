package com.miu.mdp.quiz.datasource

import com.miu.mdp.quiz.domain.User

interface UserRepository {
    suspend fun login(email: String): User?
    suspend fun register(name: String, email: String): User?
}

