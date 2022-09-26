package com.example.themeal.di

import com.example.themeal.ui.home.HomeViewModel
import com.example.themeal.ui.ingredient.IngredientViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HomeViewModel(get()) }

    single { IngredientViewModel(get()) }
}
