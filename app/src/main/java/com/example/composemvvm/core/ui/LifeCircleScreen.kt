package com.example.composemvvm.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

abstract class LifeCircleScreen {

    @Composable
    fun onCreate(callback: (LifecycleOwner) -> Unit): Boolean {
        val lifecycleOwner = LocalLifecycleOwner.current
        val ownerName = LocalLifecycleOwner.current.toString()
        lifecycleOwner.getEvent { event ->
            if (isCreatedMap[ownerName] != true && event == Lifecycle.Event.ON_CREATE) {
                isCreatedMap[ownerName] = true
                callback(lifecycleOwner)
            }
            if (event == Lifecycle.Event.ON_DESTROY) {
                isCreatedMap.remove(ownerName)
            }
        }
        return true
    }

    @Composable
    fun onResume(callback: () -> Unit): Boolean {
        LocalLifecycleOwner.current.getEvent { event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                callback()
            }
        }
        return true
    }

    @Composable
    fun onDestroy(callback: () -> Unit): Boolean {
        LocalLifecycleOwner.current.getEvent { event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                callback()
            }
        }
        return true
    }

    @Composable
    fun LifecycleOwner.getEvent(onEvent: (Lifecycle.Event) -> Unit): Boolean {
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(this) {
            val observer = LifecycleEventObserver { _, event ->
                onEvent(event)
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        return true
    }

    companion object {
        private var isCreatedMap = mutableMapOf<String, Boolean>()
    }
}