package com.logomann.bankfinder.domain.card

import com.logomann.bankfinder.domain.models.Card
import kotlinx.coroutines.flow.Flow

interface CardInteractor {
    fun searchBankInfo(request: String): Flow<Card?>
    fun getCardList(): Flow<List<Card>>
    suspend fun cleanList()
    fun getCard(cardId : Int): Flow<Card>
}