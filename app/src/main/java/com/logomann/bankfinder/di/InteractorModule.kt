package com.logomann.bankfinder.di

import com.logomann.bankfinder.domain.card.CardInteractor
import com.logomann.bankfinder.domain.card.impl.CardInteractorImpl
import com.logomann.bankfinder.domain.external.ExternalInteractor
import com.logomann.bankfinder.domain.external.impl.ExternalInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<CardInteractor> {
        CardInteractorImpl(get())
    }
    single<ExternalInteractor> {
        ExternalInteractorImpl(get())
    }
}