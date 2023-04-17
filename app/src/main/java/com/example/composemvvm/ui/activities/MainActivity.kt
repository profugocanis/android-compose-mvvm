package com.example.composemvvm.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.composemvvm.R
import com.example.composemvvm.core.LanguageHelper
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

    private val startDestination = ThirdScreen.ROUTE

    val imageHelper: ImageHelper = ImageHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                val title = remember { mutableStateOf<String?>(null) }
                val nav = rememberAnimatedNavController()
                ToolBarView(nav, startDestination, title.value)
                Navigation(nav) {
                    title.value = it
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.configureContextForLanguage(newBase))
    }

    @Composable
    fun Navigation(nav: NavHostController, onTitle: (String?) -> Unit) {
        AnimatedNavHost(nav, startDestination) {
            FirstPageScreen.createComposable(this@MainActivity, this) {
                onTitle(stringResource(id = R.string.list_screen))
                FirstPageScreen.Screen(nav)
            }

            SecondScreen.createComposable(this@MainActivity, this) {
                onTitle("SecondScreen")
                SecondScreen.Screen(nav, it)
            }

            ThirdScreen.createComposable(this@MainActivity, this) {
                onTitle("Compose Example")
                ThirdScreen.Screen(nav)
            }

            ChatScreen.createComposable(this@MainActivity, this) {
                onTitle(stringResource(R.string.chat_screen))
                ChatScreen.Screen()
            }
        }
    }

    companion object {

        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        fun refresh(activity: Activity) {
            activity.finish()
            activity.overridePendingTransition(0, 0)
            activity.startActivity(activity.intent)
            activity.overridePendingTransition(0, 0)
        }
    }
}