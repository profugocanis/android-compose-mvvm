package com.example.composemvvm.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composemvvm.ui.screens.first.FirstScreen
import com.example.composemvvm.ui.screens.second.SecondScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav = rememberNavController()
            NavHost(nav, startDestination = FirstScreen.ROUTE) {
                composable(FirstScreen.ROUTE) { FirstScreen.Screen(nav) }
                composable(SecondScreen.ROUTE) { SecondScreen.Screen(nav) }
            }
        }
    }
}