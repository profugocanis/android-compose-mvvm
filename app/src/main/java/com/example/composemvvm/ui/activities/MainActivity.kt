package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.composemvvm.core.image.ImageHelper
import com.example.composemvvm.ui.screens.chat.ChatScreen
import com.example.composemvvm.ui.screens.first.FirstPageScreen
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.example.composemvvm.ui.screens.third.ThirdScreen
import com.example.composemvvm.ui.views.ToolBarView
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {

    private val startDestination = ChatScreen.ROUTE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageHelper = ImageHelper(this)
        setContent {
            Column {
                val title = remember { mutableStateOf("") }
                val nav = rememberAnimatedNavController()
                ToolBarView(nav, startDestination, title.value)
                Navigation(nav) {
                    title.value = it
                }
            }
        }
    }

    @Composable
    fun Navigation(nav: NavHostController, onTitle: (String) -> Unit) {

        AnimatedNavHost(nav, startDestination) {
            FirstPageScreen.createComposable(this) {
                onTitle("FirstPageScreen")
                FirstPageScreen.Screen(nav)
            }

            SecondScreen.createComposable(this) {
                onTitle("SecondScreen")
                SecondScreen.Screen(nav, it)
            }

            ThirdScreen.createComposable(this) {
                onTitle("ThirdScreen")
                ThirdScreen.Screen(nav)
            }

            ChatScreen.createComposable(this) {
                onTitle("ChatScreen")
                ChatScreen.Screen()
            }
        }
    }

    companion object {
        var imageHelper: ImageHelper? = null
    }
}