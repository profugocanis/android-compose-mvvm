package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.composemvvm.ui.screens.first.FirstScreen
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation()
        }
    }

    @Composable
    fun Navigation() {
        val nav = rememberAnimatedNavController()
        AnimatedNavHost(nav, startDestination = FirstScreen.ROUTE) {
            FirstScreen.createComposable(this) {
                FirstScreen.Screen(nav)
            }

            SecondScreen.createComposable(this) {
                SecondScreen.Screen(nav, it)
            }
        }
    }
}