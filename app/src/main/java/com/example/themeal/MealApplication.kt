package com.example.themeal

import android.app.Application
import com.example.themeal.data.di.databaseModule
import com.example.themeal.data.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MealApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MealApplication)
            modules(networkModule, databaseModule)
        }
    }
}
