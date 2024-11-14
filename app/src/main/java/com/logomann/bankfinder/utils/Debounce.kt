package com.logomann.bankfinder.utils

import com.logomann.bankfinder.ui.card.CARD_MIN_BIN_LENGTH_CHARS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> debounce(
    delayMillis: Long,
    coroutineScope: CoroutineScope,
    action: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return {
        debounceJob?.cancel()
        if (it is String) {
            if (it.length >= CARD_MIN_BIN_LENGTH_CHARS) {
                debounceJob = coroutineScope.launch {
                    delay(delayMillis)
                    action(it)
                }
            }
        } else {
            debounceJob = coroutineScope.launch {
                delay(delayMillis)
                action(it)
            }
        }
    }
}
