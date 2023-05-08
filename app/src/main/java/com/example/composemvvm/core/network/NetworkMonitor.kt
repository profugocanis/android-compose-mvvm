package com.example.composemvvm.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest

class NetworkMonitor(context: Context) {

    private var isConnect = false
    private val waitingCallsQueue = mutableMapOf<String, () -> Unit>()
    private val connMgr =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            isConnect = true
            waitingCallsQueue.forEach { it.value.invoke() }
            waitingCallsQueue.clear()
        }

        override fun onLost(network: Network) {
            isConnect = false
        }
    }

    init {
        connMgr.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    fun execute(call: () -> Unit) {
        call()
        if (!isConnect) {
            waitingCallsQueue[call::class.java.toString()] = call
        }
    }

    fun onCleared() {
        waitingCallsQueue.clear()
        connMgr.unregisterNetworkCallback(networkCallback)
    }
}