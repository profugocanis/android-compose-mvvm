package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import com.example.composemvvm.ui.screens.first.FirstScreen
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav = rememberAnimatedNavController()
            AnimatedNavHost(nav, startDestination = FirstScreen.ROUTE) {
                createComposable(
                    this,
                    FirstScreen.ROUTE,
                    FirstScreen.arguments.getNavigationArguments()
                ) {
                    FirstScreen.Screen(nav)
                }

                createComposable(
                    this,
                    SecondScreen.ROUTE,
                    SecondScreen.arguments.getNavigationArguments()
                ) {
                    SecondScreen.Screen(nav, it)
                }
            }
        }
    }

    private fun createComposable(
        builder: NavGraphBuilder,
        route: String,
        arguments: List<NamedNavArgument>,
        content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
    ) {
        return builder.composable(
            route,
            arguments = arguments,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentScope.SlideDirection.Right,
                    animationSpec = tween(300)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentScope.SlideDirection.Left,
                    animationSpec = tween(300)
                )
            },
            content = content
        )
    }
}