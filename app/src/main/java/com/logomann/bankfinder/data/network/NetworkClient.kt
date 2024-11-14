package com.logomann.bankfinder.data.network

interface NetworkClient {
    suspend fun doRequest(dto: Any): Response
}