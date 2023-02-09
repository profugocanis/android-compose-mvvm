package com.example.composemvvm.ui.screens.third

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen

object ThirdScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen(nav: NavController,) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "ThirdScreen")
        }
    }
}