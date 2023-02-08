package com.example.composemvvm

import android.app.Application
import com.example.composemvvm.di.useCasesModule
import com.example.composemvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(viewModelModule, useCasesModule)
        }
    }
}