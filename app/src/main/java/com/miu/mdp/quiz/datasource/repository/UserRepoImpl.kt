package com.miu.mdp.quiz.datasource.repository

import com.miu.mdp.quiz.datasource.dao.UserDao
import com.miu.mdp.quiz.entity.User
import kotlin.random.Random
import kotlin.random.nextInt

class UserRepoImpl(
    private val userDao: UserDao
): UserRepository {


    override suspend fun login(userId: String): User? {
        return userDao.getUser(userId)
    }

    override suspend fun register(name: String, email: String): String {
        val userId = Random.nextInt(1000..9999)
        userDao.insertUser(
            User(
                userId = userId.toString(),
                name = name,
                email = email
            )
        )
        return userId.toString()
    }
}
