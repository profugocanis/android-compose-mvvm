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
import com.google.accompanist.navigation.animation.composable

abstract class BaseScreen : ComposableUtils() {

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

    fun navigate(nav: NavController, args: Map<String, Any> = mapOf()) {
        val parameters = arguments.createParameters(args)
        nav.navigate("$CLEAR_ROUTE?$parameters")
    }

    @OptIn(ExperimentalAnimationApi::class)
    fun createComposable(
        builder: NavGraphBuilder,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
    ) {
        val duration = 300
        val left = AnimatedContentScope.SlideDirection.Left
        val right = AnimatedContentScope.SlideDirection.Right
        return builder.composable(
            ROUTE,
            arguments = arguments.getNavigationArguments(),
            enterTransition = {
                slideIntoContainer(left, animationSpec = tween(duration))
            },
            exitTransition = {
                slideOutOfContainer(left, animationSpec = tween(duration))
            },
            popEnterTransition = {
                slideIntoContainer(right, animationSpec = tween(duration))
            },
            popExitTransition = {
                slideOutOfContainer(right, animationSpec = tween(duration))
            },
            content = content
        )
    }
}