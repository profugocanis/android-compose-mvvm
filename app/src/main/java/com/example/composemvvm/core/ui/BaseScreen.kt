package com.example.composemvvm.core.ui

import android.content.Context
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.composemvvm.core.NavigationArguments
import com.example.composemvvm.extentions.isRtl
import com.example.composemvvm.extentions.showInfoDialog
import com.google.accompanist.navigation.animation.composable

abstract class BaseScreen : ComposableUtils() {

    open val arguments: NavigationArguments = NavigationArguments()

    private val CLEAR_ROUTE = this::class.simpleName.toString()
    val ROUTE: String get() = "$CLEAR_ROUTE?${arguments.getBaseUrl()}"

    fun navigate(nav: NavController, args: Map<String, Any> = mapOf()) {
        val parameters = arguments.createParameters(args)
        nav.navigate("$CLEAR_ROUTE?$parameters")
    }

    @Composable
    fun onDestroy(nav: NavController, callBack: () -> Unit): Boolean {
        onStop {
            if (nav.previousBackStackEntry?.destination?.route != ROUTE) {
                callBack()
            }
        }
        return true
    }

    @Composable
    fun ShowError(error: Throwable) {
        val message = error.localizedMessage
        getContext().showInfoDialog(message) { }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun createComposable(
        context: Context,
        builder: NavGraphBuilder,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
    ) {
        val isRtl = context.isRtl
        val duration = 300
        val targetOffset = if (isRtl) 200 else -200
        val leftDirection = if (isRtl) AnimatedContentScope.SlideDirection.Right else AnimatedContentScope.SlideDirection.Left
        val rightDirection = if (isRtl) AnimatedContentScope.SlideDirection.Left else AnimatedContentScope.SlideDirection.Right
        return builder.composable(
            ROUTE,
            arguments = arguments.getNavigationArguments(),
            enterTransition = {
                slideIntoContainer(leftDirection, animationSpec = tween(duration))
            },
            exitTransition = {
                slideOutOfContainer(
                    leftDirection,
                    animationSpec = tween(duration),
                    targetOffset = { targetOffset })
            },
            popEnterTransition = {
                slideIntoContainer(
                    rightDirection,
                    initialOffset = { targetOffset },
                    animationSpec = tween(duration)
                )
            },
            popExitTransition = {
                slideOutOfContainer(rightDirection, animationSpec = tween(duration))
            },
            content = content
        )
    }
}