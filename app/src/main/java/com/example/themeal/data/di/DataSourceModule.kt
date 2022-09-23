package com.example.themeal.data.di

import com.example.themeal.data.source.DataSource
import com.example.themeal.data.source.MealDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<DataSource.MealDataSource> { MealDataSource(get(), get()) }
}
