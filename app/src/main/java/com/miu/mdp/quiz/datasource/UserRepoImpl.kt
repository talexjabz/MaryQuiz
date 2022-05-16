package com.miu.mdp.quiz.datasource

import com.miu.mdp.quiz.domain.User
import kotlin.random.Random
import kotlin.random.nextInt

class UserRepoImpl(
    private val userDao: UserDao
): UserRepository {


    override suspend fun login(email: String): User? {
        return userDao.getUser(email)
    }

    override suspend fun register(name: String, email: String): User? {
        val userId = Random.nextInt(1000..9999)
        val newUser = User(
            userId = userId.toString(),
            name = name,
            email = email
        )
        userDao.insertUser(
            newUser
        )
        return newUser
    }
}
