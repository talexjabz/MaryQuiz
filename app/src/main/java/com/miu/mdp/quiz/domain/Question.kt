package com.miu.mdp.quiz.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    val type: String,
    @Embedded(prefix = "text_") val textAnswer: TextAnswer? = null,
    @Embedded(prefix = "check_") val checkAnswer: CheckAnswer? = null,
    @Embedded(prefix = "radio_") val radioAnswer: RadioAnswer? = null
): Serializable
