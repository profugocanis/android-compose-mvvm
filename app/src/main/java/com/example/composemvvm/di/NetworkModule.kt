package com.example.composemvvm.di

import com.example.composemvvm.network.MoviesNetworkApi
import org.koin.dsl.module

val networkModule = module {
    single {
        MoviesNetworkApi().createNetworkService()
    }
}