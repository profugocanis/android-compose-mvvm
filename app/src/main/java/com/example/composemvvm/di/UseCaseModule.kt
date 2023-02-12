package com.example.composemvvm.di

import com.example.composemvvm.usecases.GetMessageListUseCase
import com.example.composemvvm.usecases.GetProductsUseCase
import com.example.composemvvm.usecases.SendMessageUseCase
import org.koin.dsl.module

val useCasesModule = module {

    factory { GetProductsUseCase() }
    factory { GetMessageListUseCase() }
    factory { SendMessageUseCase() }
}