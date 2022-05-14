package com.miu.mdp.quiz.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "check_answer")
data class CheckAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val options: List<String>,
    val correct: List<Int> //can optimize this to use a set O(1)
) : Answer<List<Int>>, Serializable {
    override fun computeAnswer(answer: List<Int>): Boolean {
        if (answer.size != correct.size) return (false)

        for (a in answer) {
            if (!correct.contains(a)) return (false)
        }
        return (true)
    }
}
