package com.example.composemvvm.ui.screens.second

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.example.composemvvm.core.BaseScreen
import com.example.composemvvm.logget
import com.example.composemvvm.models.Product
import org.koin.androidx.compose.koinViewModel

object SecondScreen : BaseScreen() {

    const val ROUTE = "ScreenTwo"

    fun open(nav: NavController, product: Product) {
        nav.navigate(ROUTE)
    }

    @Composable
    fun Screen(
        nav: NavController,
        viewModel: SecondViewModel = koinViewModel()
    ) {

        onResume {
            logget("Two onResume")
        }
        onStop {
            logget("Two onStop")
        }

        val value = rememberSaveable() { mutableStateOf(0) }

        Column {
            Text(text = "ScreenTwo! ${value.value}")
            Button(onClick = { nav.popBackStack() }) {
                Text(text = "Back")
            }
            Button(onClick = {
                value.value += 1
            }) {
                Text(text = "Incress")
            }
        }
    }
}