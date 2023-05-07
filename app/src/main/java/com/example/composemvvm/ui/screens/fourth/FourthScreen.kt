package com.example.composemvvm.ui.screens.fourth

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.logget
import org.koin.androidx.compose.koinViewModel

object FourthScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen(viewModel: FourthViewModel = koinViewModel()) {

        val screenState: FourthScreenState = viewModel.getState()

        onCreate {
            logget("onCreate")
            viewModel.start()
        }

        onResume {
            logget("onResume")
        }

        onDestroy {
            logget("onDestroy")
        }

        Content(screenState)
    }

    @Composable
    private fun Content(
        screenState: FourthScreenState
    ) {
        var high by remember { mutableStateOf(16.dp) }
        val highAnimate =
            animateDpAsState(targetValue = high, animationSpec = tween(durationMillis = 1000))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Text(text = "FourthScreen ${screenState.value}")

            Button(
                onClick = {
                    high = if (high == 100.dp) {
                        16.dp
                    } else {
                        100.dp
                    }
                },
                modifier = Modifier
                    .padding(top = highAnimate.value)
                    .height(highAnimate.value)
                    .onGloballyPositioned {
                        logget(it.size)
                    }
            ) {
                Text(text = "Product")
            }
        }
    }
}