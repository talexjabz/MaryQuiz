package com.miu.mdp.quiz.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miu.mdp.quiz.domain.*

@Database(
    entities = [
        User::class,
        TextAnswer::class,
        RadioAnswer::class,
        Question::class,
        CheckAnswer::class,
        Result::class,
        QuestionAnswerHistory::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateTypeConverter::class,
    ListConverter::class
)
abstract class MaryQuizDB : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getResultDao(): ResultDao
    abstract fun getQuestionDao(): QuestionDao
    abstract fun getQuestionAnswerHistoryDao(): QuestionHistoryDao

    companion object {
        @Volatile
        private var instance: MaryQuizDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): MaryQuizDB {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDB(context).also {
                    instance = it
                }
            }
        }

        private fun buildDB(context: Context) = Room
            .databaseBuilder(context, MaryQuizDB::class.java, "OurDB")
            .build()
    }
}
