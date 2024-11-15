package com.logomann.bankfinder.ui.card.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logomann.bankfinder.domain.card.CardInteractor
import com.logomann.bankfinder.domain.external.ExternalInteractor
import com.logomann.bankfinder.ui.card.CardInfoScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardScreenViewModel(
    private val cardInteractor: CardInteractor,
    private val externalInteractor: ExternalInteractor
) : ViewModel() {
    private val _state = MutableStateFlow<CardInfoScreenState>(CardInfoScreenState.Default)
    val state: StateFlow<CardInfoScreenState> = _state

    fun getCard(cardId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            cardInteractor.getCard(cardId).collect { card ->
                _state.value = CardInfoScreenState.Loaded(card)
            }
        }
    }

    fun openLink(url: String) {
        externalInteractor.openLink(url)
    }

    fun openNavigator(coordinates: Pair<Int, Int>) {
        externalInteractor.openNavigator(coordinates)
    }

    fun callNumber(number: String) {
        externalInteractor.callNumber(number)
    }
}