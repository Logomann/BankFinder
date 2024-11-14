package com.logomann.bankfinder.ui.card

import com.logomann.bankfinder.domain.models.Card

sealed class CardScreenState {
    data object Default : CardScreenState()
    data class Loaded(val card: Card) : CardScreenState()
}