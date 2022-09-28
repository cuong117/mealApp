package com.example.themeal.data.source

import com.example.themeal.data.model.CategoryResponse
import com.example.themeal.data.model.IngredientResponse
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.model.MealResponse

interface DataSource {

    interface MealDataSource {
        suspend fun getMealByFirstLetter(letter: String): MealResponse

        suspend fun getCategories(): CategoryResponse

        suspend fun getMealRecent(): List<MealCollapse>

        suspend fun getListIngredient(): IngredientResponse
    }
}
