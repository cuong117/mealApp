package com.example.themeal.data.source

import com.example.themeal.data.model.AreaResponse
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

    override suspend fun getMealByName(name: String): MealResponse {
        return apiService.getMealByName(name)
    }

    override suspend fun getArea(): AreaResponse {
        return apiService.getArea()
    }

    override suspend fun getCategories(): CategoryResponse {
        return apiService.getCategory()
    }

    override suspend fun getMealByCategory(name: String): MealResponse {
        return apiService.getMealByCategory(name)
    }

    override suspend fun getMealRecent(): List<MealCollapse> {
        return mealDAO.getListRecentMeal()
    }

    override suspend fun getListIngredient(): IngredientResponse {
        return apiService.getListIngredient()
    }

    override suspend fun getMealById(id: String): MealResponse {
        return apiService.getMealDetail(id)
    }

    override suspend fun insertRecentMeal(mealCollapse: MealCollapse) {
        mealDAO.insertMeal(mealCollapse)
    }
}
