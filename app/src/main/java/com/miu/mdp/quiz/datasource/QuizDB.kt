package com.miu.mdp.quiz.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miu.mdp.quiz.datasource.dao.QuestionDao
import com.miu.mdp.quiz.datasource.dao.ResultDao
import com.miu.mdp.quiz.datasource.dao.UserDao
import com.miu.mdp.quiz.entity.*
import com.miu.mdp.quiz.entity.converter.DateTypeConverter
import com.miu.mdp.quiz.entity.converter.ListConverter

@Database(
    entities = [
        User::class,
        TextAnswer::class,
        RadioAnswer::class,
        Question::class,
        CheckAnswer::class,
        Result::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateTypeConverter::class,
    ListConverter::class
)
abstract class QuizDB : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getResultDao(): ResultDao
    abstract fun getQuestionDao(): QuestionDao

    companion object {
        @Volatile
        private var instance: QuizDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): QuizDB {
            return instance?: synchronized(LOCK) {
                instance?: buildDB(context).also {
                    instance = it
                }
            }
        }

        private fun buildDB(context: Context) = Room
            .databaseBuilder(context, QuizDB::class.java, "Quiz DB")
            .build()
    }
}
