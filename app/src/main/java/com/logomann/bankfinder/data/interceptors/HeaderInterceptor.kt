package com.logomann.bankfinder.data.interceptors

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {
    private const val USER_AGENT_AUTHORIZATION = "Accept-Version: 3"
    private const val CONTENT_TYPE = "Content-Type: application/json"

    private val headers = Headers.Builder()
        .add(USER_AGENT_AUTHORIZATION)
        .add(CONTENT_TYPE)
        .build()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .headers(headers)
            .build()

        return chain.proceed(request)
    }
}