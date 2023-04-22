package com.example.composemvvm.ui.screens.fourth

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.core.network.Source
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FourthViewModel(
    application: Application
) : BaseStateViewModel(application) {

    override val uiState = FourthScreenState()

    fun start() {
        viewModelScope.launch {
            (1..100).forEach {
                delay(1_000)
                uiState.handleTestValue(Source.Success(it))
                if (it == 3) {
                    val error = Source.Error(Exception("Opps, something went wrong"))
                    uiState.handleTestValue(error)
                }
            }
        }
    }
}