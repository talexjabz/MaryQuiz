package com.miu.mdp.quiz.datasource

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromIntString(value: String): List<Int> {
        return Gson().fromJson(value, object : TypeToken<List<Int>>(){}.type)
    }

    @TypeConverter
    fun toIntString(list: List<Int>): String {
        return Gson().toJson(list)
    }
}
