package com.example.composemvvm.core.network

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

abstract class BaseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalHttpUrl = original.url

        val urlBuilder = originalHttpUrl.newBuilder()

        applyParams(urlBuilder)

        val requestBuilder = original.newBuilder()
            .url(urlBuilder.build())

        applyHeaders(requestBuilder)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    open fun applyHeaders(requestBuilder: Request.Builder) {}

    open fun applyParams(urlBuilder: HttpUrl.Builder) {}
}