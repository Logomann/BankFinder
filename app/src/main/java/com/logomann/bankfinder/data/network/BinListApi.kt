package com.logomann.bankfinder.data.network

import com.logomann.bankfinder.data.card.CardSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BinListApi {

    @GET("{request}")
    suspend fun getBankInfo(@Path("request") request: String): CardSearchResponse
}