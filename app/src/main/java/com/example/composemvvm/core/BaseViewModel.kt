package com.example.composemvvm.core

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    val scope: CoroutineScope = uiScope

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}