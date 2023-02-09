package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Product
import kotlinx.coroutines.delay

class GetProductsUseCase : BaseUseCase() {

    suspend operator fun invoke(): Source<List<Product>> {
        delay(500)
        return Source.Success(
            (0..50).map { Product(id = it, name = "Product $it") }
        )
    }
}