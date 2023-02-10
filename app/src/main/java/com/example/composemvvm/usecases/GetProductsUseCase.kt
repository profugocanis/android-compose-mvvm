package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Product
import kotlinx.coroutines.delay

class GetProductsUseCase : BaseUseCase() {

    private val pageSize = 15

    suspend operator fun invoke(page: Int): Source<List<Product>> {
        delay(500)
        return Source.Success(
            (page * pageSize until page * pageSize + pageSize).map { Product(id = it, name = "Product $it") }
        )
    }
}