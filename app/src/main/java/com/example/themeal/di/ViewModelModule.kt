package com.example.themeal.di

import com.example.themeal.ui.favorite.FavoriteViewModel
import com.example.themeal.ui.home.HomeViewModel
import com.example.themeal.ui.ingredient.IngredientViewModel
import com.example.themeal.ui.listmeal.ListMealViewModel
import com.example.themeal.ui.mealdetail.MealDetailViewModel
import com.example.themeal.ui.search.SearchViewModel
import com.example.themeal.ui.search.searchIngredient.IngredientResultViewModel
import com.example.themeal.ui.search.searchmeal.SearchResultViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get()) }

    viewModel { IngredientViewModel(get()) }

    viewModel { SearchViewModel(get()) }

    viewModel { SearchResultViewModel(get()) }

    viewModel { ListMealViewModel(get()) }

    viewModel { IngredientResultViewModel() }

    viewModel { MealDetailViewModel(get()) }

    viewModel { FavoriteViewModel(get(), get()) }
}
