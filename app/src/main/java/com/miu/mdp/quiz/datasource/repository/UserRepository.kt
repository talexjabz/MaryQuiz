package com.miu.mdp.quiz.datasource.repository

import com.miu.mdp.quiz.entity.User

interface UserRepository {
    suspend fun login(userId: String): User?
    suspend fun register(name: String, email: String): String
}

