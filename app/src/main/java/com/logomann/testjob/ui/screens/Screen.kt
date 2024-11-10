package com.logomann.testjob.ui.screens

sealed class Screen(val route: String){
    data object CardInfoScreen : Screen(route = "mainScreen")
    data object CardHistoryScreen : Screen(route = "historyScreen")
}