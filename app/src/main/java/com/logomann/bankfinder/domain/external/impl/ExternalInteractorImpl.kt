package com.logomann.bankfinder.domain.external.impl

import com.logomann.bankfinder.domain.external.ExternalInteractor
import com.logomann.bankfinder.domain.external.ExternalNavigator

class ExternalInteractorImpl(
    private val externalNavigator: ExternalNavigator
) : ExternalInteractor {
    override fun openLink(url: String) {
        externalNavigator.openLink(url)
    }

    override fun openNavigator(coordinates: Pair<Int, Int>) {
        externalNavigator.openNavigator(coordinates)
    }

    override fun callNumber(number: String) {
        externalNavigator.callNumber(number)
    }
}