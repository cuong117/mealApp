package com.example.themeal.data.di

import com.example.themeal.data.repository.MealRepository
import com.example.themeal.data.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository.MealRepository> { MealRepository(get()) }
}
