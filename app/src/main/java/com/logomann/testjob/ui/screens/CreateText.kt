package com.logomann.testjob.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CreateText(
    modifier: Modifier = Modifier,
    fieldName: String,
    fieldData: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            fontSize = 12.sp,
            text = fieldName)
        Text(
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            text = fieldData)
    }


}