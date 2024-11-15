package com.logomann.bankfinder.ui.card

import com.logomann.bankfinder.domain.models.Card

sealed class CardInfoScreenState {
    data object Default : CardInfoScreenState()
    data class Loaded(val card: Card) : CardInfoScreenState()
}