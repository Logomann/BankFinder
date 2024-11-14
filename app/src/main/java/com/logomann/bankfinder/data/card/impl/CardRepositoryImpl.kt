package com.logomann.bankfinder.data.card.impl


import com.logomann.bankfinder.data.card.CardSearchRequest
import com.logomann.bankfinder.data.card.CardSearchResponse
import com.logomann.bankfinder.data.db.AppDatabase
import com.logomann.bankfinder.data.dto.converters.CardDbConverter
import com.logomann.bankfinder.data.network.NetworkClient
import com.logomann.bankfinder.data.network.RESULT_CODE_NO_INTERNET
import com.logomann.bankfinder.data.network.RESULT_CODE_SUCCESS
import com.logomann.bankfinder.domain.card.CardRepository
import com.logomann.bankfinder.domain.models.Bank
import com.logomann.bankfinder.domain.models.Card
import com.logomann.bankfinder.domain.models.Country
import com.logomann.bankfinder.domain.models.Number
import com.logomann.bankfinder.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CardRepositoryImpl(
    private val networkClient: NetworkClient,
    private val appDatabase: AppDatabase,
    private val cardDbConverter: CardDbConverter
) : CardRepository {
    override fun searchBankInfo(request: String): Flow<Resource<Card>> = flow {
        val response = networkClient.doRequest(CardSearchRequest(request))
        when (response.resultCode) {
            RESULT_CODE_NO_INTERNET -> {
                emit(Resource.Error(null))
            }

            RESULT_CODE_SUCCESS -> {
                with((response as CardSearchResponse)) {
                    val data = Card(
                        id = 0,
                        bankBin = request.toString(),
                        network = scheme,
                        type = type,
                        brand = brand,
                        prepaid = prepaid,
                        country = Country(
                            name = country?.name,
                            emoji = country?.emoji,
                            latitude = country?.latitude,
                            longitude = country?.longitude
                        ),
                        bank = Bank(
                            name = bank?.name,
                            url = bank?.url,
                            phone = bank?.phone,
                            city = bank?.city
                        ),
                        number = Number(
                            length = number?.length,
                            luhn = number?.luhn
                        )
                    )
                    val listOfCards = appDatabase.cardDao().getCardList().map {
                        cardDbConverter.map(it)
                    }
                    var alreadyExist = false
                    listOfCards.forEach {
                        if (it.bank?.name == data.bank?.name) {
                            alreadyExist = true
                        }
                    }
                    if (!alreadyExist && data.bank?.name != null) {
                        appDatabase.cardDao().insertCard(cardEntity = cardDbConverter.map(data))
                    }
                    emit(Resource.Success(data))
                }
            }

            else -> {
                emit(Resource.Error(""))
            }
        }
    }

    override fun getCardList(): Flow<List<Card>> = flow {
        val listOfCards = appDatabase.cardDao().getCardList().map {
            cardDbConverter.map(it)
        }
        emit(listOfCards)
    }

    override suspend fun cleanList() {
       appDatabase.cardDao().deleteAll()
    }

    override fun getCard(cardId: Int): Flow<Card> = flow{
        val card = cardDbConverter.map(appDatabase.cardDao().getCard(cardId))
        emit(card)
    }
}