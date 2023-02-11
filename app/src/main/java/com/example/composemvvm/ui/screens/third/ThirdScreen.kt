package com.example.composemvvm.ui.screens.third

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.logget
import com.example.composemvvm.ui.screens.first.FirstPageScreen
import com.example.composemvvm.ui.screens.first.FirstScreen

object ThirdScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen(nav: NavController) {

        onDestroy(nav = nav) {
            logget("ThirdScreen onDestroy")
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "ThirdScreen")

            Button(onClick = {
                FirstPageScreen.open(nav)
            }) {
                Text(text = "Products")
            }
        }
    }
}