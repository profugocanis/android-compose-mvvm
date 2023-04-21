package com.example.composemvvm.core.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.composemvvm.logget

abstract class ComposableUtils {

    @Composable
    fun getContext() = LocalContext.current

    @Composable
    fun getActivity() = LocalContext.current as AppCompatActivity

    companion object {
        private var isCreatedMap = mutableMapOf<String, Boolean>()
    }

    @Composable
    fun onCreate(callback: (LifecycleOwner) -> Unit): Boolean {
        val lifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(this) {
            val observer = LifecycleEventObserver { _, event ->
                if (isCreatedMap[lifecycleOwner.toString()] != true && event == Lifecycle.Event.ON_CREATE) {
                    isCreatedMap[lifecycleOwner.toString()] = true
                    callback(lifecycleOwner)
                }
                if (event == Lifecycle.Event.ON_DESTROY) {
                    isCreatedMap.remove(lifecycleOwner.toString())
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
        return true
    }

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
        var state by remember { mutableStateOf(Lifecycle.Event.ON_CREATE) }
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