package com.logomann.bankfinder.domain.card.impl

import com.logomann.bankfinder.domain.card.CardInteractor
import com.logomann.bankfinder.domain.card.CardRepository
import com.logomann.bankfinder.domain.models.Card
import com.logomann.bankfinder.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CardInteractorImpl(private val cardRepository: CardRepository) : CardInteractor {
    override fun searchBankInfo(request: String): Flow<Card?> {
        return cardRepository.searchBankInfo(request).map { result ->
            when (result) {
                is Resource.Error -> null
                is Resource.Success -> result.data
            }
        }
    }

    override fun getCardList(): Flow<List<Card>> {
        return cardRepository.getCardList()
    }

    override suspend fun cleanList() {
        cardRepository.cleanList()
    }

    override fun getCard(cardId: Int): Flow<Card> {
        return cardRepository.getCard(cardId)
    }
}