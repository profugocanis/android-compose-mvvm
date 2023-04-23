package com.example.composemvvm.usecases

import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.network.MoviesApi

class GetMovieUseCase(
    private val api: MoviesApi
) : BaseUseCase() {

    suspend operator fun invoke(imdbID: String): Source<Movie> {
        return remoteDataSource.executeNetworkRequest {
            api.getMovie(imdbID, "full")
        }
    }
}