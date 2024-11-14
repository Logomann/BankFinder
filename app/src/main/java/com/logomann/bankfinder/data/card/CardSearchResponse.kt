package com.logomann.bankfinder.data.card

import com.logomann.bankfinder.data.dto.BankDto
import com.logomann.bankfinder.data.dto.CountryDto
import com.logomann.bankfinder.data.dto.NumberDto
import com.logomann.bankfinder.data.network.Response

class CardSearchResponse(
    val number: NumberDto?,
    val scheme: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: CountryDto?,
    val bank: BankDto?,
) : Response()
