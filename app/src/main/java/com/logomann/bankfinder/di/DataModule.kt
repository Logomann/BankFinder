package com.logomann.bankfinder.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.google.gson.Gson
import com.logomann.bankfinder.data.db.AppDatabase
import com.logomann.bankfinder.data.interceptors.HeaderInterceptor
import com.logomann.bankfinder.data.interceptors.LoggingInterceptor
import com.logomann.bankfinder.data.network.BinListApi
import com.logomann.bankfinder.data.network.NetworkClient
import com.logomann.bankfinder.data.network.impl.RetrofitNetworkClient
import com.logomann.bankfinder.utils.App
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://lookup.binlist.net"
val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single<BinListApi> {
        val client = OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor)
            .addInterceptor(HeaderInterceptor)
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BinListApi::class.java)
    }

    single<App> {
        androidContext() as App
    }

    factory<NetworkClient> {
        RetrofitNetworkClient(
            get(), androidContext().getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
        )
    }
    factory { Gson() }

}