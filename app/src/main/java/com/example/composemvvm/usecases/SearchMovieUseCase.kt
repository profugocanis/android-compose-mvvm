package com.example.composemvvm.usecases

import com.example.composemvvm.core.network.Source
import com.example.composemvvm.network.MoviesApi
import com.example.composemvvm.network.responses.MoviesSearchResponse

class SearchMovieUseCase(
    private val api: MoviesApi
) : BaseUseCase() {

    suspend operator fun invoke(searchText: String, page: Int): Source<MoviesSearchResponse> {
        return remoteDataSource.executeNetworkRequest {
            api.searchMovies(text = searchText, year = "", page = page.toString())
        }
    }
}