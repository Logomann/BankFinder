package com.logomann.bankfinder.data.dto

data class CountryDto(
    val numeric: Int?,
    val alpha2: String?,
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val latitude: Int?,
    val longitude: Int?
)
