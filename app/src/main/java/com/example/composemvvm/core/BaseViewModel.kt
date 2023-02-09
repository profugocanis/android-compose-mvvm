package com.example.composemvvm.core

import android.app.Application
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun <T : Any> createSourceMutableState(): MutableState<Source<T>> {
        return mutableStateOf(
            Source.Processing(),
            policy = neverEqualPolicy()
        )
    }
}