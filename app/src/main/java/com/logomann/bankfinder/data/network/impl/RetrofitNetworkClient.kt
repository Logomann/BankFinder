package com.logomann.bankfinder.data.network.impl

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.logomann.bankfinder.data.card.CardSearchRequest
import com.logomann.bankfinder.data.network.BinListApi
import com.logomann.bankfinder.data.network.NetworkClient
import com.logomann.bankfinder.data.network.RESULT_CODE_BAD_REQUEST
import com.logomann.bankfinder.data.network.RESULT_CODE_NO_INTERNET
import com.logomann.bankfinder.data.network.RESULT_CODE_SERVER_ERROR
import com.logomann.bankfinder.data.network.RESULT_CODE_SUCCESS
import com.logomann.bankfinder.data.network.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(
    private val binListApi: BinListApi,
    private val connectivityManager: ConnectivityManager
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = RESULT_CODE_NO_INTERNET }
        }
        val response = if (dto is CardSearchRequest) {
            withContext(Dispatchers.IO) {
                try {
                    val resp = binListApi.getBankInfo(dto.request)
                    resp.apply {
                        resultCode = RESULT_CODE_SUCCESS
                    }
                } catch (_: Throwable) {
                    Response().apply { resultCode = RESULT_CODE_SERVER_ERROR }
                }
            }
        } else {
            Response().apply { resultCode = RESULT_CODE_BAD_REQUEST }
        }
        return response
    }

    private fun isConnected(): Boolean {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}