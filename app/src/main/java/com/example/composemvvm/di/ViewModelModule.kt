package com.example.composemvvm.di

import com.example.composemvvm.ui.screens.chat.ChatViewModel
import com.example.composemvvm.ui.screens.first.FirstViewModel
import com.example.composemvvm.ui.screens.second.SecondViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        FirstViewModel(
            application = get(),
            getProductsUseCase = get(),
        )
    }

    viewModel {
        SecondViewModel(
            application = get(),
        )
    }

    viewModel {
        ChatViewModel(
            application = get(),
            getMessageListUseCase = get()
        )
    }
}