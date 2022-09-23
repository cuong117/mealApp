package com.example.themeal.di

import com.example.themeal.ui.favorite.FavoriteFragment
import com.example.themeal.ui.home.HomeFragment
import com.example.themeal.ui.ingredient.IngredientFragment
import org.koin.dsl.module

val fragmentModule = module {
    single { HomeFragment() }
    single { IngredientFragment() }
    single { FavoriteFragment() }
}
