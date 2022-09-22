package com.example.themeal.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themeal.data.model.MealCollapse

@Dao
interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealCollapse)

    @Query("select * from recent")
    suspend fun getListRecentMeal(): List<MealCollapse>
}
