package com.example.themeal.data.source

import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.source.database.MealDAO

class FavoriteMealDataSource(private val mealDAO: MealDAO) : DataSource.FavoriteDataSource {

    override suspend fun getFavoriteMealId(): List<FavoriteMeal> {
        return mealDAO.getFavoriteMeal()
    }

    override suspend fun insertFavoriteMeal(meal: FavoriteMeal) {
        mealDAO.insertFavoriteMeal(meal)
    }

    override suspend fun deleteFavoriteMeal(meal: FavoriteMeal) {
        mealDAO.deleteFavoriteMeal(meal)
    }
}
