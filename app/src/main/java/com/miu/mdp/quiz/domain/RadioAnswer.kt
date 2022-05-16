package com.miu.mdp.quiz.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "radio_answer")
data class RadioAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val options: List<String>,
    val correct: Int
) : Answer<Int>, Serializable {
    override fun computeAnswer(answer: Int): Boolean {
        return (answer == correct)
    }
}
