package com.logomann.testjob.di

import androidx.room.Room
import com.logomann.testjob.data.db.AppDatabase
import com.logomann.testjob.utils.App
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<App> {
        androidContext() as App
    }

}