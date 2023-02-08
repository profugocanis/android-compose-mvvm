package com.example.composemvvm.core

import android.app.Activity
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController

abstract class BaseScreen {

    private val CLEAR_ROUTE = this::class.simpleName.toString()
    val ROUTE: String
        get() {
            var route = CLEAR_ROUTE
            val arguments = arguments.getBaseUrl()
            if (arguments.isNotEmpty()) {
                route += "?$arguments"
            }
            return route
        }

    open val arguments: NavigationArguments = NavigationArguments()

    fun navigate(nav: NavController, args: Map<String, String> = mapOf()) {
        val parameters = NavigationArguments.createParameters(args)
        nav.navigate("$CLEAR_ROUTE?$parameters")
    }

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