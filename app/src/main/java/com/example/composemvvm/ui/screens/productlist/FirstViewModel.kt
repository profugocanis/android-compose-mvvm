package com.example.composemvvm.ui.screens.productlist

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.usecases.GetProductsUseCase
import kotlinx.coroutines.launch

class FirstViewModel(
    application: Application,
    private val getProductsUseCase: GetProductsUseCase
) : BaseStateViewModel(application) {

    override var uiState = FirstScreenState()

    private var page = 0

    init {
        load()
    }

    fun load(isProgress: Boolean = true) {
        if (isProgress) {
            uiState.handleProducts(Source.Processing())
        }
        page = 0
        viewModelScope.launch {
            val source = getProductsUseCase(page)
            uiState.handleProducts(source, page)
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            val source = getProductsUseCase(page)
//            val source = Source.Error(Exception("Opps!"))
            uiState.handleProducts(source, page)
        }
    }
}