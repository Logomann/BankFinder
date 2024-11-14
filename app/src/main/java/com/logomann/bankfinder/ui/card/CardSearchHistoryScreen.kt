package com.logomann.bankfinder.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.logomann.bankfinder.R
import com.logomann.bankfinder.ui.card.viewmodel.CardSearchHistoryViewModel
import com.logomann.bankfinder.ui.screens.Screen
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSearchHistoryScreen(
    navController: NavController,
    viewModel: CardSearchHistoryViewModel = koinInject<CardSearchHistoryViewModel>()
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        if (viewModel.listOfCards.isNotEmpty()) {
            val (topBar, searchList, header) = createRefs()
            TopAppBar(
                title = { Text(stringResource(R.string.search_history)) },
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(header) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topBar.bottom)
                    }
            ) {
                Text(text = stringResource(R.string.bin))
                Text(text = stringResource(R.string.bank_name))

            }
            val btn = createRef()
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(searchList) {
                        top.linkTo(header.bottom, 8.dp)
                        height = Dimension.fillToConstraints
                        bottom.linkTo(btn.top)
                    }
            ) {
                items(viewModel.listOfCards.size) { item ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .clickable(
                                onClick = {
                                    navController.navigate(
                                        Screen.CardInfoScreen.route + "/${viewModel.listOfCards[item].id}"
                                    )
                                }
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = viewModel.listOfCards[item].bankBin.toString()
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier
                                .weight(2f),
                            textAlign = TextAlign.End,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            text = viewModel.listOfCards[item].bank?.name.toString()
                        )

                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

            }

            Button(
                modifier = Modifier.constrainAs(btn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                onClick = {
                    viewModel.cleanList()
                    navController.navigateUp()
                }) {
                Text(text = stringResource(R.string.clean_list))
            }
        }
    }
}
