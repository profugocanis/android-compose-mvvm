package com.example.composemvvm.core

import android.app.Application
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var screenState: Any? = null

    fun <T> rememberScreenState(creator: @DisallowComposableCalls () -> T): T {
        if (screenState == null) {
            screenState = creator()
        }
        return screenState as T
    }

    fun <T : Any> createSourceMutableState(): MutableState<Source<T>> {
        return mutableStateOf(
            Source.Processing(),
            policy = neverEqualPolicy()
        )
    }

    override fun onCleared() {
        super.onCleared()
        screenState = null
    }
}