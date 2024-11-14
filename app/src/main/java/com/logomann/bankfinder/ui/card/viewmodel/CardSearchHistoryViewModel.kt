package com.logomann.bankfinder.ui.card.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logomann.bankfinder.domain.card.CardInteractor
import com.logomann.bankfinder.domain.models.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardSearchHistoryViewModel(
    private val cardInteractor: CardInteractor
) : ViewModel() {
    val listOfCards = mutableStateListOf<Card>()

    init {
        getCards()
    }

    private fun getCards() {
        viewModelScope.launch(Dispatchers.IO) {
            cardInteractor.getCardList().collect { list ->
                listOfCards.clear()
                listOfCards.addAll(list)
            }
        }
    }

    fun cleanList() {
        viewModelScope.launch(Dispatchers.IO) {
            cardInteractor.cleanList()
            listOfCards.clear()
        }
    }
}