package com.example.themeal.data.repository

import com.example.themeal.base.BaseRepository
import com.example.themeal.data.source.DataSource

class MealRepository(private val mealSource: DataSource.MealDataSource) : Repository.MealRepository,
    BaseRepository() {

    override suspend fun getMealByFirstLetter(letter: String) =
        getResult { mealSource.getMealByFirstLetter(letter) }

    override suspend fun getCategories() = getResult { mealSource.getCategories() }

    override suspend fun getListMealRecent() = getResult { mealSource.getMealRecent() }
}
