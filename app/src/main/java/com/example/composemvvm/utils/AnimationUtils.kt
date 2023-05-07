package com.example.composemvvm.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

object AnimationUtils {

    @Composable
    fun alpha(condition: () -> Boolean): State<Float> {
        return animFloatState(from = 0f, to = 1f) {
            condition()
        }
    }

    @Composable
    fun animFloatState(
        from: Float,
        to: Float,
        duration: Int = 300,
        condition: () -> Boolean,
    ): State<Float> {
        var high by remember { mutableStateOf(0f) }
        high = if (condition()) {
            to
        } else {
            from
        }
        return animateFloatAsState(
            targetValue = high,
            animationSpec = tween(durationMillis = duration)
        )
    }
}