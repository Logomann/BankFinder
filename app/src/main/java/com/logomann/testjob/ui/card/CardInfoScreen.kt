package com.logomann.testjob.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.logomann.testjob.R
import com.logomann.testjob.ui.screens.CreateText
import com.logomann.testjob.ui.screens.Screen
import com.logomann.testjob.ui.theme.TestJobTheme

const val CARD_NUMBER_LENGTH_CHARS = 16

@Composable
fun CardInfoScreen(
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val isLoading = false
        val hasHistory = true
        val (inputField, label) = createRefs()
        val text = remember { mutableStateOf("") }
        TextField(
            value = text.value,
            onValueChange = {
                if (it.length <= CARD_NUMBER_LENGTH_CHARS) {
                    text.value = it
                }
            },
            label = {
                Text(text = stringResource(R.string.input_bank_bin))
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
        val (network, type, bank, brand, prepaid, card, country) = createRefs()
        if (isLoading) {
            val indicator = createRef()
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(indicator) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        } else {
            CreateText(
                modifier = Modifier.constrainAs(network) {
                    start.linkTo(parent.start)
                    top.linkTo(inputField.bottom, 24.dp)
                    end.linkTo(type.start)
                },
                fieldName = "Network",
                fieldData = "Visa"
            )
            CreateText(
                modifier = Modifier.constrainAs(type) {
                    start.linkTo(network.end)
                    top.linkTo(network.top)
                    end.linkTo(bank.start)
                },
                fieldName = "Type",
                fieldData = "Debit"
            )
            CreateText(
                modifier = Modifier.constrainAs(bank) {
                    start.linkTo(type.end)
                    top.linkTo(type.top)
                    end.linkTo(parent.end)
                },
                fieldName = "Bank",
                fieldData = "Jyske Bank, Hjørring\n" +
                        "www.jyskebank.dk\n" +
                        "+4589893300"
            )
            CreateText(
                modifier = Modifier.constrainAs(brand) {
                    start.linkTo(parent.start)
                    top.linkTo(bank.bottom, 24.dp)
                    end.linkTo(prepaid.start)
                },
                fieldName = "Brand",
                fieldData = "Visa/Dankort"
            )
            CreateText(
                modifier = Modifier.constrainAs(prepaid) {
                    start.linkTo(brand.end)
                    top.linkTo(brand.top)
                    end.linkTo(card.start)
                },
                fieldName = "Prepaid",
                fieldData = "No"
            )
            Column(
                modifier = Modifier.constrainAs(card) {
                    start.linkTo(prepaid.end)
                    top.linkTo(prepaid.top)
                    end.linkTo(parent.end)
                }
            ) {
                Text(text = "Card Number", fontSize = 12.sp)
                Row {
                    CreateText(
                        fieldName = "Length",
                        fieldData = "16"
                    )
                    CreateText(
                        modifier = Modifier.padding(start = 16.dp),
                        fieldName = "LUHN",
                        fieldData = "Yes"
                    )
                }
            }
            CreateText(
                modifier = Modifier.constrainAs(country) {
                    start.linkTo(parent.start)
                    top.linkTo(card.bottom, 24.dp)
                    end.linkTo(parent.end)
                },
                fieldName = "Country",
                fieldData = "\uD83C\uDDE9\uD83C\uDDF0 Denmark\n" +
                        "(latitude: 56, longitude: 10)"
            )
        }
        if (hasHistory) {
            val btn = createRef()
            Button(
                modifier = Modifier.constrainAs(btn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(country.bottom, 16.dp)
                },
                onClick = {
                    navController.navigate(Screen.CardHistoryScreen.route)
                }) {
                Text("Search history")

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