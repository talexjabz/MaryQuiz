package com.miu.mdp.quiz.entity

interface Answer<T> {
    fun computeAnswer(answer: T): Boolean
}
