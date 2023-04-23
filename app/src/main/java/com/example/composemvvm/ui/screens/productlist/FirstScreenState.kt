package com.example.composemvvm.ui.screens.productlist

import androidx.compose.runtime.mutableStateListOf
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.models.Product
import com.example.composemvvm.utils.ScrollHelper

class FirstScreenState : BaseScreenState() {

    val products = mutableStateListOf<Product>()
    val scroll = ScrollHelper()

    fun handleProducts(
        source: Source<List<Product>>,
        page: Int = 0
    ) {
        when (source) {
            is Source.Processing -> {
                products.clear()
                products.addAll(productMock)
            }
            is Source.Success -> {
                scroll.isScrollEnable.value = true
                if (page == 0) {
                    products.clear()
                }
                products.addAll(source.data ?: listOf())
                scroll.isLastPage = page >= 2
                scroll.refreshing.isRefreshing = false
            }
            is Source.Error -> context?.showInfoDialog(source.getErrorMessage())
        }
    }
}
