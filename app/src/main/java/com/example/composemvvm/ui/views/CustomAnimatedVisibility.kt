package com.example.composemvvm.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayoutScope

@Composable
fun ConstraintLayoutScope.ConstraintLoadView(
    isLoading: Boolean,
    loadView: ConstrainedLayoutReference,
) {
    AnimatedVisibility(visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.constrainAs(loadView) {
            top.linkTo(parent.top, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
        CircularProgressIndicator(strokeWidth = 4.dp)
    }
}