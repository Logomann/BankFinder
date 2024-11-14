package com.logomann.bankfinder.di


import com.logomann.bankfinder.ui.card.viewmodel.CardScreenViewModel
import com.logomann.bankfinder.ui.card.viewmodel.CardSearchHistoryViewModel
import com.logomann.bankfinder.ui.card.viewmodel.CardSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CardSearchViewModel(get(), get())
    }
    viewModel {
        CardSearchHistoryViewModel(get())
    }
    viewModel {
        CardScreenViewModel(get(), get())
    }
}