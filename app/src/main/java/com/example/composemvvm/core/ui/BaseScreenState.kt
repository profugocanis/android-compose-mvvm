package com.example.composemvvm.core.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

abstract class BaseScreenState {

    protected var context: Context? = null
        private set

    @Composable
    open fun InitComposable() {
        context = LocalContext.current
    }
}