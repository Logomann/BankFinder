package com.logomann.bankfinder.domain.models

data class Card(
    val id: Int,
    val bankBin: String?,
    val network: String?,
    val type: String?,
    val brand: String?,
    val prepaid: Boolean?,
    val country: Country?,
    val bank: Bank?,
    val number: Number?
)
