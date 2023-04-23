package com.example.composemvvm.network

import com.example.composemvvm.core.network.BaseInterceptor
import com.example.composemvvm.utils.BuildUtils
import okhttp3.HttpUrl

class AuthenticationInterceptor : BaseInterceptor() {

    override fun applyParams(urlBuilder: HttpUrl.Builder) {
        urlBuilder.addQueryParameter("apikey", BuildUtils.API_KEY)
    }
}