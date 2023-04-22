package com.example.composemvvm.ui.screens.fourth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.extentions.showInfoDialog

class FourthScreenState : BaseScreenState() {

    var valueState by mutableStateOf(0)
        private set

    fun handleTestValue(source: Source<Int>) {
        when (source) {
            is Source.Processing -> Unit
            is Source.Success -> {
                valueState = source.data ?: 0
            }

            is Source.Error -> {
                context?.showInfoDialog(source.getErrorMessage())
            }
        }
    }
}