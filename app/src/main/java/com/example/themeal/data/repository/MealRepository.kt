package com.example.themeal.data.repository

import com.example.themeal.base.BaseRepository
import com.example.themeal.data.model.AreaResponse
import com.example.themeal.data.model.MealResponse
import com.example.themeal.data.source.DataSource
import com.example.themeal.util.DataResult

class MealRepository(private val mealSource: DataSource.MealDataSource) : Repository.MealRepository,
    BaseRepository() {

    override suspend fun getMealByFirstLetter(letter: String) =
        getResult { mealSource.getMealByFirstLetter(letter) }

    override suspend fun getMealByName(name: String): DataResult<MealResponse> =
        getResult { mealSource.getMealByName(name) }

    override suspend fun getArea() = getResult { mealSource.getArea() }

    override suspend fun getCategories() = getResult { mealSource.getCategories() }

    override suspend fun getListMealRecent() = getResult { mealSource.getMealRecent() }

    override suspend fun getListIngredient() = getResult { mealSource.getListIngredient() }
}
