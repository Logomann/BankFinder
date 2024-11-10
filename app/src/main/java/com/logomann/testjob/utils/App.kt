package com.logomann.testjob.utils

import android.app.Application
import com.logomann.testjob.di.dataModule
import com.logomann.testjob.di.interactorModule
import com.logomann.testjob.di.repositoryModule
import com.logomann.testjob.di.viewModelModule
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