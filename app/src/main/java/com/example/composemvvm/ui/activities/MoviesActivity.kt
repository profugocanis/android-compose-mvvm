package com.example.composemvvm.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composemvvm.ui.screens.moviedetail.MovieDetailScreen
import com.example.composemvvm.ui.screens.moviessearch.MoviesSearchScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
class MoviesActivity : AppCompatActivity() {

    private val startDestination = MoviesSearchScreen.route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val nav = rememberAnimatedNavController()
            Navigation(nav)
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

    companion object {

        fun open(context: Context) {
            val intent = Intent(context, MoviesActivity::class.java)
            context.startActivity(intent)
        }
    }
}