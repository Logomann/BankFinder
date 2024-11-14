package com.logomann.bankfinder.utils

import android.app.Application
import com.logomann.bankfinder.di.dataModule
import com.logomann.bankfinder.di.interactorModule
import com.logomann.bankfinder.di.repositoryModule
import com.logomann.bankfinder.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    private lateinit var application: Application
    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(this@App)
            modules(dataModule, interactorModule, repositoryModule, viewModelModule)
        }
    }
}