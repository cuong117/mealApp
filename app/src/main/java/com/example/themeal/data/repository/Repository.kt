package com.example.themeal.data.repository

import com.example.themeal.data.model.CategoryResponse
import com.example.themeal.data.model.IngredientResponse
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.model.MealResponse
import com.example.themeal.util.DataResult

interface Repository {

    interface MealRepository {
        suspend fun getMealByFirstLetter(letter: String): DataResult<MealResponse>

        suspend fun getCategories(): DataResult<CategoryResponse>

        suspend fun getListMealRecent(): DataResult<List<MealCollapse>>

        suspend fun getListIngredient(): DataResult<IngredientResponse>
    }
}
