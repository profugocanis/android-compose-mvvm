package com.example.composemvvm.core.ui

import android.content.Context
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.composemvvm.core.NavigationArguments
import com.example.composemvvm.extentions.isRtl
import com.google.accompanist.navigation.animation.composable

abstract class BaseScreen : LifecycleScreen() {

    open val arguments: NavigationArguments = NavigationArguments()

    private val className = this::class.simpleName.toString()
    val route: String get() = "$className?${arguments.getBaseUrl()}"

    protected fun navigate(nav: NavController, args: Map<String, Any> = mapOf()) {
        val parameters = arguments.createParameters(args)
        nav.navigate("$className?$parameters")
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

        val leftDirection =
            if (isRtl) AnimatedContentTransitionScope.SlideDirection.Right else AnimatedContentTransitionScope.SlideDirection.Left
        val rightDirection =
            if (isRtl) AnimatedContentTransitionScope.SlideDirection.Left else AnimatedContentTransitionScope.SlideDirection.Right
        return builder.composable(
            route,
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