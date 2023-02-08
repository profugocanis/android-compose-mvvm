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
import kotlinx.coroutines.launch

class FirstViewModel(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase
) : BaseViewModel(application) {

    var products by mutableStateOf<Source<List<Product>>>(Source.Processing())

    init {
        load()
    }

    fun load() {
        products = Source.Processing()
        viewModelScope.launch {
            products = getProductsUseCase()
//            delay(1_000)
//            products = Source.Error(Exception("Oops, something went wrong"))
        }
    }
}