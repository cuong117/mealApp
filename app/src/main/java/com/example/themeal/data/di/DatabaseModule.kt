package com.example.themeal.data.di

import android.content.Context
import androidx.room.Room
import com.example.themeal.data.source.database.ListConverter
import com.example.themeal.data.source.database.MealDatabase
import org.koin.dsl.module

val databaseModule = module {
    single { provideDatabase(get()) }
}

private fun provideDatabase(context: Context): MealDatabase {
    return Room.databaseBuilder(
        context.applicationContext, MealDatabase::class.java,
        MealDatabase.DATABASE_NAME
    )
        .addTypeConverter(ListConverter())
        .build()
}
