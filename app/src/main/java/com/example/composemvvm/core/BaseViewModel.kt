package com.example.composemvvm.core

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.network.Source
import kotlinx.coroutines.cancel

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    fun <T : Any> createSourceMutableState(): MutableState<Source<T>> {
        return mutableStateOf(
            Source.Processing(),
            policy = neverEqualPolicy()
        )
    }

    fun <T : Any> createSourceMutableLiveData(): MutableLiveData<Source<T>> {
        return MutableLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}