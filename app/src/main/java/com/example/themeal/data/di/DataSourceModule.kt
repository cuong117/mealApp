package com.example.themeal.data.di

import com.example.themeal.data.source.DataSource
import com.example.themeal.data.source.FavoriteMealDataSource
import com.example.themeal.data.source.MealDataSource
import com.example.themeal.data.source.RecentSearchDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<DataSource.MealDataSource> { MealDataSource(get(), get()) }

    single<DataSource.RecentSearchDataSource> { RecentSearchDataSource(get()) }

    single<DataSource.FavoriteDataSource> { FavoriteMealDataSource(get()) }
}
