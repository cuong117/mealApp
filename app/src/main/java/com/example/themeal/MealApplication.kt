package com.example.themeal

import android.app.Application
import com.example.themeal.data.di.dataSourceModule
import com.example.themeal.data.di.databaseModule
import com.example.themeal.data.di.networkModule
import com.example.themeal.data.di.repositoryModule
import com.example.themeal.di.fragmentModule
import com.example.themeal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MealApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val modules = listOf(
            networkModule,
            databaseModule,
            fragmentModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule
        )
        startKoin {
            androidContext(this@MealApplication)
            modules(modules)
        }
    }
}
