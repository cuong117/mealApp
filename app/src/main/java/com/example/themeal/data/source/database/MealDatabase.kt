package com.example.themeal.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.themeal.constant.Constant
import com.example.themeal.data.model.MealCollapse

@Database(entities = [MealCollapse::class], version = Constant.ROOM_VERSION)
@TypeConverters(ListConverter::class)
abstract class MealDatabase : RoomDatabase() {

    abstract fun getMealDAO(): MealDAO

    companion object {
        const val DATABASE_NAME = "mealDB"
        const val RECENT_TABLE = "recent"
    }
}
