package com.example.themeal.data.source

import com.example.themeal.data.model.AreaResponse
import com.example.themeal.data.model.CategoryResponse
import com.example.themeal.data.model.IngredientResponse
import com.example.themeal.data.model.MealCollapse
import com.example.themeal.data.model.MealResponse
import com.example.themeal.data.model.RecentSearch

interface DataSource {

    interface MealDataSource {
        suspend fun getMealByFirstLetter(letter: String): MealResponse

        suspend fun getMealByName(name: String): MealResponse

        suspend fun getArea(): AreaResponse

        suspend fun getCategories(): CategoryResponse

        suspend fun getMealRecent(): List<MealCollapse>

        suspend fun getListIngredient(): IngredientResponse
    }

    interface RecentSearchDataSource {
        suspend fun getAllKeyWord(): List<RecentSearch>

        suspend fun addNewKeyWord(recentSearch: RecentSearch)

        suspend fun deleteKeyWord(recentSearch: RecentSearch)
    }
}
