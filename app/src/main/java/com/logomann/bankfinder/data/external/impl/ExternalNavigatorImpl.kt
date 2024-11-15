package com.logomann.bankfinder.data.external.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.logomann.bankfinder.domain.external.ExternalNavigator

class ExternalNavigatorImpl(private val context: Context) : ExternalNavigator {

    override fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        if (url.startsWith("http://") || url.startsWith("https://")) {
            intent.data = Uri.parse(url)
        } else {
            intent.data = Uri.parse("http://$url")
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openNavigator(coordinates: Pair<Int, Int>) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:?q=${coordinates.first},${coordinates.second}")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun callNumber(number: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$number")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}