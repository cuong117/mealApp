package com.example.themeal.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.themeal.data.source.database.MealDatabase

@Entity(
    tableName = MealDatabase.FAVORITE_TABLE,
    indices = [Index(value = ["mealId"], unique = true)]
)
data class FavoriteMeal(
    val mealId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
