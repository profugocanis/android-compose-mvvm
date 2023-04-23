package com.example.composemvvm.network

import com.example.composemvvm.core.network.BaseNetworkAPIConfig
import com.example.composemvvm.utils.BuildUtils

class MoviesNetworkApi : BaseNetworkAPIConfig<MoviesApi>() {

    override fun isSslDisable() = false
    override fun getBaseUrl() = BuildUtils.API_URL
    override fun isLoggingEnable() = true
    override fun getServiceClass() = MoviesApi::class.java
    override fun applyInterceptor() = listOf(
        AuthenticationInterceptor()
    )
}