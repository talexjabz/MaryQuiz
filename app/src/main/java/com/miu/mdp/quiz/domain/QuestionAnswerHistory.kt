package com.miu.mdp.quiz.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QuestionAnswerHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var userId: String = "",
    val question: String,
    val userAnswer: String,
    val correctAnswer: String
)
