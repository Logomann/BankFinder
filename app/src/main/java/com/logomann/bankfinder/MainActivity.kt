package com.logomann.bankfinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.logomann.bankfinder.ui.card.CardScreen
import com.logomann.bankfinder.ui.card.CardSearchHistoryScreen
import com.logomann.bankfinder.ui.card.CardSearchScreen
import com.logomann.bankfinder.ui.screens.Screen
import com.logomann.bankfinder.ui.theme.TestJobTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TestJobTheme {
                Scaffold(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                        .fillMaxSize()
                ) { _ ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.CardSearchScreen.route
                    ) {
                        composable(Screen.CardSearchScreen.route) {
                            CardSearchScreen(navController = navController)
                        }
                        composable(Screen.CardHistoryScreen.route) {
                            CardSearchHistoryScreen(navController = navController)
                        }
                        composable(
                            route = Screen.CardInfoScreen.route + "/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType }),
                        ) {stackEntry ->
                            val cardId = stackEntry.arguments?.getInt("cardId")
                            CardScreen(
                                cardId = cardId,
                                navController = navController)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestJobTheme {
        CardSearchScreen(rememberNavController())
    }
}