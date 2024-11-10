package com.logomann.testjob

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.logomann.testjob.ui.card.CardInfoScreen
import com.logomann.testjob.ui.card.CardSearchHistoryScreen
import com.logomann.testjob.ui.screens.Screen
import com.logomann.testjob.ui.theme.TestJobTheme

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
                        startDestination = Screen.CardInfoScreen.route
                    ) {
                        composable(Screen.CardInfoScreen.route) {
                            CardInfoScreen(navController = navController)
                        }
                        composable(Screen.CardHistoryScreen.route) {
                            CardSearchHistoryScreen(navController = navController)
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
        CardInfoScreen(rememberNavController())
    }
}