package com.example.themeal.data.di

import android.content.Context
import androidx.room.Room
import com.example.themeal.data.source.database.ListConverter
import com.example.themeal.data.source.database.MealDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideMealDatabase(get()) }
    single { provideMealDAO(get()) }
    single { provideRecentSearchDAO(get()) }
}

private fun provideMealDatabase(context: Context): MealDatabase {
    return Room.databaseBuilder(
        context.applicationContext, MealDatabase::class.java,
        MealDatabase.DATABASE_NAME
    )
        .addTypeConverter(ListConverter())
        .build()
}

private fun provideMealDAO(mealDatabase: MealDatabase) = mealDatabase.getMealDAO()

private fun provideRecentSearchDAO(mealDatabase: MealDatabase) = mealDatabase.getRecentSearchDAO()
