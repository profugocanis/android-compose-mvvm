package com.example.composemvvm.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.network.NetworkMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val networkMonitor = NetworkMonitor(application)

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        networkMonitor.onCleared()
    }

    protected fun launchWithSafeNetwork(block: suspend CoroutineScope.() -> Unit) {
        networkMonitor.execute {
            viewModelScope.launch {
                block()
            }
        }
    }
}