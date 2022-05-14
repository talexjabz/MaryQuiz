package com.miu.mdp.quiz.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val correct: Int,
    val wrong: Int,
    val date: Date
)


