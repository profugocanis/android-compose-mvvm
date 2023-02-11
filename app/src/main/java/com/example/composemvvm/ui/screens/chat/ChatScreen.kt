package com.example.composemvvm.ui.screens.chat

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen

object ChatScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen() {

    }
}