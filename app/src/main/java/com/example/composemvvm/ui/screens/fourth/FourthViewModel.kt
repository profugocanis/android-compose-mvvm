package com.example.composemvvm.ui.screens.fourth

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.logget
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FourthViewModel(
    application: Application
) : BaseViewModel(application) {

    val testValueLiveData = createSourceMutableLiveData<Int>()

    fun start() {
        viewModelScope.launch {
            (1..100).forEach {
                delay(1_000)
                testValueLiveData.value = Source.Success(it)
            }
        }
    }
}