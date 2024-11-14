package com.logomann.bankfinder.ui.card

import com.logomann.bankfinder.domain.models.Card

const val EMPTY_ERROR_CODE = 0
const val SERVER_ERROR_CODE = -1

sealed class CardSearchScreenState() {
    data object Default : CardSearchScreenState()
    data object Loading : CardSearchScreenState()
    data class Error(val errorCode: Int) : CardSearchScreenState()
    data class Content(val card: Card) : CardSearchScreenState()
}