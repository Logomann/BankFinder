package com.logomann.bankfinder.domain.external

interface ExternalNavigator {
    fun openLink(url: String)
    fun openNavigator(coordinates: Pair<Int, Int>)
    fun callNumber(number: String)
}