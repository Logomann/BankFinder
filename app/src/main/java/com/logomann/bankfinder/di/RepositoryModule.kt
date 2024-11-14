package com.logomann.bankfinder.di

import com.logomann.bankfinder.data.card.impl.CardRepositoryImpl
import com.logomann.bankfinder.data.dto.converters.CardDbConverter
import com.logomann.bankfinder.data.external.impl.ExternalNavigatorImpl
import com.logomann.bankfinder.domain.card.CardRepository
import com.logomann.bankfinder.domain.external.ExternalNavigator
import org.koin.dsl.module
import org.koin.dsl.single
import kotlin.math.sin

val repositoryModule = module {

    single<CardRepository> {
        CardRepositoryImpl(get(), get(), get())
    }
    factory {
        CardDbConverter(get())
    }
    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
}