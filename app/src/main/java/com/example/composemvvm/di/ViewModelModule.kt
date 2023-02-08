package com.example.composemvvm.di

import com.example.composemvvm.ui.screens.one.OneViewModel
import com.example.composemvvm.ui.screens.two.TwoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        OneViewModel(
            application = get(),
            getProductsUseCase = get(),
        )
    }

    viewModel {
        TwoViewModel(
            application = get(),
        )
    }
}