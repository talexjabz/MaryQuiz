package com.miu.mdp.quiz.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "text_answer")
data class TextAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val correct: String
): Answer<String>, Serializable {
    override fun computeAnswer(answer: String): Boolean {
        return (answer==correct)
    }
}
