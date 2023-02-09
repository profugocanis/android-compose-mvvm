package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.composemvvm.ui.screens.first.FirstScreen
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.example.composemvvm.ui.screens.third.ThirdScreen
import com.example.composemvvm.ui.views.ToolBarView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private val startDestination = FirstScreen.ROUTE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                val nav = rememberAnimatedNavController()
                ToolBarView(nav = nav, startDestination)
                Navigation(nav = nav)
            }
        }
    }

    @Composable
    fun Navigation(nav: NavHostController) {

        AnimatedNavHost(nav, startDestination) {
            FirstScreen.createComposable(this) {
                FirstScreen.Screen(nav)
            }

            SecondScreen.createComposable(this) {
                SecondScreen.Screen(nav, it)
            }

            ThirdScreen.createComposable(this) {
                ThirdScreen.Screen(nav)
            }
        }
    }
}