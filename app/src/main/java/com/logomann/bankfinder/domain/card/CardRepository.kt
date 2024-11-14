package com.logomann.bankfinder.domain.card

import com.logomann.bankfinder.domain.models.Card
import com.logomann.bankfinder.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun searchBankInfo(request: String): Flow<Resource<Card>>
    fun getCardList(): Flow<List<Card>>
    suspend fun cleanList()
    fun getCard(cardId : Int): Flow<Card>
}