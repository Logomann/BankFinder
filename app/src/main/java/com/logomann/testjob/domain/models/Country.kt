package com.logomann.testjob.domain.models

data class Country(
    val numeric: Int,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int
)
