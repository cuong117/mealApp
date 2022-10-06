package com.example.themeal.data.repository

import com.example.themeal.base.BaseRepository
import com.example.themeal.data.model.FavoriteMeal
import com.example.themeal.data.source.DataSource

class FavoriteMealRepository(private val favoriteDataSource: DataSource.FavoriteDataSource) :
    Repository.FavoriteMealRepository, BaseRepository() {

    override suspend fun getFavoriteMealId() = getResult { favoriteDataSource.getFavoriteMealId() }

    override suspend fun insertFavoriteMeal(meal: FavoriteMeal) =
        getResult { favoriteDataSource.insertFavoriteMeal(meal) }

    override suspend fun deleteFavoriteMeal(meal: FavoriteMeal) =
        getResult { favoriteDataSource.deleteFavoriteMeal(meal) }
}
