package com.example.themeal.data.source.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ListConverter {

    @TypeConverter
    fun fromJson(value: String): List<String?>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String?>): String? {
        return Gson().toJson(list)
    }
}
