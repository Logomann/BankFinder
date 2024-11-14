package com.logomann.bankfinder.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.logomann.bankfinder.R
import com.logomann.bankfinder.domain.models.Card

@Composable
fun CreateCardTable(
    modifier: Modifier = Modifier,
    card: Card,
    openNavigator: () -> Unit,
    openLink: () -> Unit,
    callNumber: () -> Unit
) {
    val dataMap = mapOf(
        stringResource(R.string.bank_bin) to card.bankBin,
        stringResource(R.string.network) to card.network,
        stringResource(R.string.type) to card.type,
        stringResource(R.string.bank) to card.bank?.name,
        stringResource(R.string.bank_site) to card.bank?.url,
        stringResource(R.string.bank_phone) to card.bank?.phone,
        stringResource(R.string.bank_city) to card.bank?.city,
        stringResource(R.string.brand) to card.brand,
        stringResource(R.string.prepaid) to card.prepaid,
        stringResource(R.string.length) to card.number?.length,
        stringResource(R.string.luhn) to card.number?.luhn,
        stringResource(R.string.country) to
                "${card.country?.emoji} ${card.country?.name}",
        stringResource(R.string.coordinates) to
                "(latitude: ${card.country?.latitude}, " +
                "longitude: ${card.country?.longitude})"
    )
    val coordinates = stringResource(R.string.coordinates)
    val phone = stringResource(R.string.bank_phone)
    val site = stringResource(R.string.bank_site)

    LazyColumn(
        modifier = modifier
    ) {
        items(count = dataMap.size) {
            val map = dataMap.toList()
            if (map[it].second != null) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                when (map[it].first) {
                                    coordinates -> {
                                        openNavigator()
                                    }

                                    phone -> {
                                        callNumber()
                                    }

                                    site -> {
                                        openLink()
                                    }
                                }
                            })
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = map[it].first
                    )
                    Text(
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        text = map[it].second.toString()
                    )
                }
                HorizontalDivider(thickness = 1.dp)
            }
        }
    }

}