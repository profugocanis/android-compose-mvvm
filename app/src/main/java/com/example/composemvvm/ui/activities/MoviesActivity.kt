package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composemvvm.ui.screens.moviedetail.MovieDetailScreen
import com.example.composemvvm.ui.screens.moviessearch.MoviesSearchScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
class MoviesActivity : AppCompatActivity() {

    private val startDestination = MoviesSearchScreen.ROUTE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                val nav = rememberAnimatedNavController()
                Navigation(nav)
            }
        }
    }

    @Composable
    fun Navigation(nav: NavHostController) {
        AnimatedNavHost(nav, startDestination) {
            MoviesSearchScreen.createComposable(this@MoviesActivity, this) {
                MoviesSearchScreen.Screen(nav)
            }

            MovieDetailScreen.createComposable(this@MoviesActivity, this) {
                MovieDetailScreen.Screen(it)
            }
        }
    }
}