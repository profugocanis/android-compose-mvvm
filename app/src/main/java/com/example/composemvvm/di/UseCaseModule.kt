package com.example.composemvvm.di

import com.example.composemvvm.usecases.GetProductsUseCase
import org.koin.dsl.module

val useCasesModule = module {

    factory { GetProductsUseCase() }
}