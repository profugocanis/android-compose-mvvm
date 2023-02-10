package com.example.composemvvm.core.ui

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.composemvvm.logget
import com.example.composemvvm.ui.screens.first.FirstScreen

abstract class ComposableUtils {

    @Composable
    fun getContext() = LocalContext.current as Activity

    @Composable
    fun onResume(callback: () -> Unit): Boolean {
        val lifecycleEvent = rememberLifecycleEvent()
        LaunchedEffect(lifecycleEvent) {
            if (lifecycleEvent == Lifecycle.Event.ON_RESUME) callback()
        }
        return true
    }

    @Composable
    fun onStop(callback: () -> Unit): Boolean {
        val lifecycleEvent = rememberLifecycleEvent()
        LaunchedEffect(lifecycleEvent) {
            if (lifecycleEvent == Lifecycle.Event.ON_STOP) callback()
        }
        return true
    }

    @Composable
    private fun rememberLifecycleEvent(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current): Lifecycle.Event {
        var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                state = event
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        return state
    }
}