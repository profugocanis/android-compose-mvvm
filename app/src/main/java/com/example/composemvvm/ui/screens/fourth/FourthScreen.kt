package com.example.composemvvm.ui.screens.fourth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavController
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.logget
import com.example.composemvvm.models.Product
import com.example.composemvvm.ui.screens.second.SecondScreen
import org.koin.androidx.compose.koinViewModel

object FourthScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen(nav: NavController, viewModel: FourthViewModel = koinViewModel()) {

        val screenState = viewModel.rememberScreenState { FourthScreenState() }

        val context = LocalContext.current
        onCreate { lifecycleOwner ->
            logget("onCreate")
            viewModel.testValueLiveData.observe(lifecycleOwner) {
                when (it) {
                    is Source.Processing -> Unit
                    is Source.Success -> {
                        screenState.valueState.value = it.data ?: 0
                    }
                    is Source.Error -> {
                        context.showInfoDialog(it.getErrorMessage())
                    }
                }
            }
            viewModel.start()
        }

        onResume {
            logget("onResume")
        }

        onDestroy {
            logget("onDestroy")
        }

        Content(nav, screenState)
    }

    @Composable
    private fun Content(nav: NavController, screenState: FourthScreenState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "FourthScreen ${screenState.valueState.value}")

            Button(onClick = {
                SecondScreen.open(nav, Product())
            }) {
                Text(text = "Product")
            }
        }
    }
}