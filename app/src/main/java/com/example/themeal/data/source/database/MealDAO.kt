package com.example.themeal.data.source.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.model.MealCollapse

@Dao
interface MealDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealCollapse)

    @Query("select * from " + MealDatabase.RECENT_TABLE)
    suspend fun getListRecentMeal(): List<MealCollapse>

    @Query("select * from ${MealDatabase.FAVORITE_TABLE}")
    suspend fun getFavoriteMeal(): List<FavoriteMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMeal(meal: FavoriteMeal)

    @Delete
    suspend fun deleteFavoriteMeal(meal: FavoriteMeal)
}
