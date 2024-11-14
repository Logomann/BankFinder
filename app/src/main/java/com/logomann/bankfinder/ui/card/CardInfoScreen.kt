package com.logomann.bankfinder.ui.card

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.logomann.bankfinder.R
import com.logomann.bankfinder.ui.card.viewmodel.CardScreenViewModel
import com.logomann.bankfinder.ui.screens.CreateCardTable
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CardInfoScreen(
    cardId: Int?,
    navController: NavController,
    viewModel: CardScreenViewModel = koinInject<CardScreenViewModel>()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    if (cardId != null) {
        viewModel.getCard(cardId)
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {

            val (topBar, table) = createRefs()

            when (state.value) {
                CardScreenState.Default -> {}
                is CardScreenState.Loaded -> {
                    TopAppBar(
                        title = { Text(stringResource(R.string.card_info)) },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier.constrainAs(topBar) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                    )
                    val card = (state.value as CardScreenState.Loaded).card
                    CreateCardTable(
                        card = card,
                        modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(table) {
                                start.linkTo(parent.start)
                                top.linkTo(topBar.bottom, 8.dp)
                            },
                        openNavigator = {
                            if (card.country?.latitude != null) {
                                val pair = Pair(
                                    card.country.latitude,
                                    card.country.longitude!!
                                )
                                viewModel.openNavigator(pair)
                            }
                        },
                        openLink = {
                            viewModel.openLink(card.bank?.url.toString())
                        },
                        callNumber = {
                            viewModel.callNumber(card.bank?.phone.toString())
                        }
                    )
                }
            }

        }
    }
}