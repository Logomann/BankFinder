package com.logomann.testjob.domain.models

data class Card(
    val id: Int,
    val bankBin: Int,
    val network: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: Country,
    val bank: Bank
)
