package com.example.composemvvm.core.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.composemvvm.core.NavigationArguments
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
    fun ShowError(error: Throwable) {
        val message = error.localizedMessage
        getContext().showInfoDialog(message) { }
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun createComposable(
        builder: NavGraphBuilder,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
    ) {
        val duration = 300
        val leftDirection = AnimatedContentScope.SlideDirection.Left
        val rightDirection = AnimatedContentScope.SlideDirection.Right
        return builder.composable(
            ROUTE,
            arguments = arguments.getNavigationArguments(),
            enterTransition = {
                slideIntoContainer(leftDirection, animationSpec = tween(duration))
            },
            exitTransition = {
                slideOutOfContainer(leftDirection, animationSpec = tween(duration))
            },
            popEnterTransition = {
                slideIntoContainer(rightDirection, animationSpec = tween(duration))
            },
            popExitTransition = {
                slideOutOfContainer(rightDirection, animationSpec = tween(duration))
            },
            content = content
        )
    }
}