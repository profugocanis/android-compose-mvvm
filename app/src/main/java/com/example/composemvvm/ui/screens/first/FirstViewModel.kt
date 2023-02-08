package com.example.composemvvm.ui.screens.first

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var products by mutableStateOf<Source<List<Product>>?>(null)
        private set

    init {
        load()
    }

    private fun load() {
        logget("loading...")
        products = Source.Processing()
        scope.launch {
            products = getProductsUseCase()
        }
    }
}