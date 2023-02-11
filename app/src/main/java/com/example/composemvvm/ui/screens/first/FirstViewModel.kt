package com.example.composemvvm.ui.screens.first

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.core.Source
import com.example.composemvvm.logget
import com.example.composemvvm.models.Product
import com.example.composemvvm.usecases.GetProductsUseCase
import kotlinx.coroutines.launch

class FirstViewModel(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel(application) {

    var productsState by createSourceMutableState<List<Product>>()
        private set

    var page = 0
        private set

    init {
        load()
        logget("FirstViewModel init")
    }

    fun load(isProgress: Boolean = true) {
        if (isProgress) {
            productsState = Source.Processing()
        }
        page = 0
        viewModelScope.launch {
            productsState = getProductsUseCase(page)
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            productsState = getProductsUseCase(page)
        }
    }
}