package com.example.composemvvm.ui.screens.two

import android.app.Application
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.logget

class TwoViewModel(
    application: Application
) : BaseViewModel(application) {

    init {
        logget("TwoViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        logget("TwoViewModel onCleared")
    }
}