package com.logomann.bankfinder.data.dto.converters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.logomann.bankfinder.data.db.CardEntity
import com.logomann.bankfinder.domain.models.Card

class CardDbConverter(private val gson: Gson) {
    fun map(card: Card): CardEntity {
        return CardEntity(
            cardId = 0,
            content = gson.toJson(card)
        )
    }

    fun map(cardEntity: CardEntity): Card {
        val card = cardFromGson(cardEntity.content)
        return Card(
            id = cardEntity.cardId,
            bankBin = card.bankBin,
            network = card.network,
            bank = card.bank,
            type = card.type,
            brand = card.brand,
            prepaid = card.prepaid,
            country = card.country,
            number = card.number
        )
    }

    private fun cardFromGson(card: String): Card {
        val type = object : TypeToken<Card>() {}.type
        return gson.fromJson<Card>(card, type)
    }
}