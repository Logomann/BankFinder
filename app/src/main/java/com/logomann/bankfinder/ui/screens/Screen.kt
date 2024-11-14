package com.logomann.bankfinder.ui.screens

sealed class Screen(val route: String){
    data object CardSearchScreen : Screen(route = "mainScreen")
    data object CardHistoryScreen : Screen(route = "historyScreen")
    data object CardInfoScreen : Screen(route = "cardInfoScreen")
}