package com.logomann.bankfinder.ui.card.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logomann.bankfinder.domain.card.CardInteractor
import com.logomann.bankfinder.domain.external.ExternalInteractor
import com.logomann.bankfinder.domain.models.Card
import com.logomann.bankfinder.ui.card.CardSearchScreenState
import com.logomann.bankfinder.ui.card.EMPTY_ERROR_CODE
import com.logomann.bankfinder.ui.card.SERVER_ERROR_CODE
import com.logomann.bankfinder.utils.debounce
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L

class CardSearchViewModel(
    private val cardInteractor: CardInteractor,
    private val externalInteractor: ExternalInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<CardSearchScreenState>(CardSearchScreenState.Default)
    val state: StateFlow<CardSearchScreenState> = _state
    private var _hasHistoryState = MutableStateFlow<Boolean>(false)
    val hasHistoryState: StateFlow<Boolean> = _hasHistoryState
    var text by mutableStateOf("")
    private var cardSearchDebounce = debounce(
        delayMillis = SEARCH_DEBOUNCE_DELAY_MILLIS,
        coroutineScope = viewModelScope
    ) { it: String ->
        getBankInfo(it)
    }

    init {
        isDatabaseEmpty()
    }

    private fun getBankInfo(request: String) {
        _state.value = CardSearchScreenState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            cardInteractor.searchBankInfo(request).collect { result ->
                if (result != null) {
                    if (result.bank?.name == null) {
                        _state.value = CardSearchScreenState.Error(EMPTY_ERROR_CODE)
                    } else {
                        _hasHistoryState.value = true
                        _state.value = CardSearchScreenState.Content(
                            Card(
                                id = result.id,
                                network = result.network,
                                type = result.type,
                                bank = result.bank,
                                prepaid = result.prepaid,
                                country = result.country,
                                brand = result.brand,
                                bankBin = result.bankBin,
                                number = result.number
                            )
                        )
                    }

                } else {
                    _state.value = CardSearchScreenState.Error(SERVER_ERROR_CODE)
                }
            }
        }
    }

    fun search(request: String) {
            cardSearchDebounce(request)
    }

    private fun isDatabaseEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            cardInteractor.getCardList().collect { list ->
                if (list.isNotEmpty()) {
                    _hasHistoryState.value = true
                }
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