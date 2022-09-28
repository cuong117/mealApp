package com.example.themeal.data.source

import com.example.themeal.data.model.CategoryResponse
import com.example.themeal.data.model.IngredientResponse
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.model.MealResponse
import com.example.themeal.data.source.api.MealService
import com.example.themeal.data.source.database.MealDAO

class MealDataSource(
    private val apiService: MealService,
    private val mealDAO: MealDAO
) : DataSource.MealDataSource {

    override suspend fun getMealByFirstLetter(letter: String): MealResponse {
        return apiService.getMealByFirstLetter(letter)
    }

    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategory()
    }

    override suspend fun getMealRecent(): List<MealCollapse> {
        return mealDAO.getListRecentMeal()
    }

    override suspend fun getListIngredient(): IngredientResponse {
        return apiService.getListIngredient()
    }
}
