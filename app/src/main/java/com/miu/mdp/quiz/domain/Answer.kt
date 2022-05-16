package com.miu.mdp.quiz.domain

interface Answer<T> {
    fun computeAnswer(answer: T): Boolean
}
