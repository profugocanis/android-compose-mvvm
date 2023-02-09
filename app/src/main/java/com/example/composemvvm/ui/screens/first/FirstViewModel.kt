package com.example.composemvvm.ui.screens.first

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Product
import com.example.composemvvm.usecases.GetProductsUseCase
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

class FirstViewModel(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel(application) {

    var products by createSourceMutableState<List<Product>>()
        private set

    init {
        load()
    }

    fun load(isProgress: Boolean = true) {
        if (isProgress) {
            products = Source.Processing()
        }
        viewModelScope.launch {
            products = getProductsUseCase()
//            delay(1_000)
//            products = Source.Error(Exception("Oops, something went wrong"))
        }
    }
}