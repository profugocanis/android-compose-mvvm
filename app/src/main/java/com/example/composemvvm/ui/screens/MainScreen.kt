package com.example.composemvvm.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.ui.screens.third.ThirdScreen

object MainScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(onClick = {
                ThirdScreen.open(nav)
            }) {
                Text(text = "Products")
            }
        }
    }
}