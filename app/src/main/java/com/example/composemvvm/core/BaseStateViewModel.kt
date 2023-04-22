package com.example.composemvvm.core

import android.app.Application
import androidx.compose.runtime.Composable
import com.example.composemvvm.core.ui.BaseScreenState

@Suppress("UNCHECKED_CAST")
abstract class BaseStateViewModel(application: Application) : BaseViewModel(application) {

    protected abstract val uiState: BaseScreenState

    @Composable
    fun <T : BaseScreenState> getState(): T {
        uiState.InitComposable()
        return uiState as T
    }
}