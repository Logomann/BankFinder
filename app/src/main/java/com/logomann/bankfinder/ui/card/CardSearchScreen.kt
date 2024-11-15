package com.logomann.bankfinder.ui.card

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.logomann.bankfinder.R
import com.logomann.bankfinder.ui.card.viewmodel.CardSearchViewModel
import com.logomann.bankfinder.ui.screens.CreateCardTable
import com.logomann.bankfinder.ui.screens.Screen
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

const val CARD_NUMBER_LENGTH_CHARS = 16
const val CARD_MIN_BIN_LENGTH_CHARS = 6

@Composable
fun CardSearchScreen(
    navController: NavController,
    viewModel: CardSearchViewModel = koinInject<CardSearchViewModel>()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state = viewModel.state.collectAsStateWithLifecycle()

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (inputField, snack, indicator, table) = createRefs()

        TextField(
            value = viewModel.text,
            onValueChange = {
                if (it.length <= CARD_NUMBER_LENGTH_CHARS) {
                    viewModel.text = it
                    viewModel.search(it)
                }
            },
            label = {
                Text(text = stringResource(R.string.input_card_number))
            },
            trailingIcon = {
                if (viewModel.text.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.text = ""
                    }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                    }
                }

            },
            maxLines = 1,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.constrainAs(inputField) {
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                top.linkTo(parent.top, 48.dp)
            }
        )

        when (state.value) {
            is CardSearchScreenState.Content -> {
                val card = (state.value as CardSearchScreenState.Content).card
                CreateCardTable(
                    card = card,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(table) {
                            start.linkTo(parent.start)
                            top.linkTo(inputField.bottom, 8.dp)
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
                    },
                    snackbarHostState = snackbarHostState
                )
            }

            CardSearchScreenState.Default -> {}
            is CardSearchScreenState.Error -> {
                var messageError = ""
                when ((state.value as CardSearchScreenState.Error).errorCode) {
                    EMPTY_ERROR_CODE -> {
                        messageError = stringResource(R.string.no_such_bin_found)
                    }

                    SERVER_ERROR_CODE -> {
                        messageError = stringResource(R.string.connection_error)
                    }
                }
                CreateSnackbarHost(
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .constrainAs(snack) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            top.linkTo(parent.top, 16.dp)
                        })

                LaunchedEffect(Unit) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = messageError,
                            duration = SnackbarDuration.Short
                        )
                    }
                }

            }

            CardSearchScreenState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        start.linkTo(parent.start)
                        top.linkTo(inputField.bottom, 24.dp)
                        end.linkTo(parent.end)
                    }
                )
            }
        }
        val historyState = viewModel.hasHistoryState.collectAsStateWithLifecycle()

        if (historyState.value) {
            val btn = createRef()
            Button(
                modifier = Modifier
                    .constrainAs(btn) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                onClick = {
                    navController.navigate(Screen.CardHistoryScreen.route)
                }) {
                Text(stringResource(R.string.search_history))
            }
        }
    }
}

@Composable
fun CreateSnackbarHost(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier
) {
    SnackbarHost(snackbarHostState) { data ->
        Snackbar(
            containerColor = MaterialTheme.colorScheme.error,
            modifier = modifier
        ) {
            Text(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                text = data.visuals.message
            )
        }
    }
}
