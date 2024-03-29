package com.example.composemvvm.di

import com.example.composemvvm.ui.screens.chat.ChatViewModel
import com.example.composemvvm.ui.screens.productlist.FirstViewModel
import com.example.composemvvm.ui.screens.fourth.FourthViewModel
import com.example.composemvvm.ui.screens.moviedetail.MovieDetailViewModel
import com.example.composemvvm.ui.screens.moviessearch.MoviesSearchViewModel
import com.example.composemvvm.ui.screens.product.ProductViewModel
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
        ProductViewModel(
            application = get(),
        )
    }

    viewModel {
        ChatViewModel(
            application = get(),
            getMessageListUseCase = get(),
            sendMessageUseCase = get()
        )
    }

    viewModel {
        FourthViewModel(
            application = get(),
        )
    }

    viewModel {
        MoviesSearchViewModel(
            application = get(),
            searchMovieUseCase = get()
        )
    }

    viewModel {
        MovieDetailViewModel(
            application = get(),
            getMovieUseCase = get()
        )
    }
}