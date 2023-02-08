package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composemvvm.ui.screens.one.OneScreen
import com.example.composemvvm.ui.screens.two.TwoScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav = rememberNavController()
            NavHost(nav, startDestination = OneScreen.ROUTE) {
                composable(OneScreen.ROUTE) { OneScreen.Screen(nav) }
                composable(TwoScreen.ROUTE) { TwoScreen.Screen(nav) }
            }
        }
    }
}