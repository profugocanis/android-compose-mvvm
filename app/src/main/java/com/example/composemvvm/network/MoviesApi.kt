package com.example.composemvvm.network

import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.network.responses.MoviesSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/")
    suspend fun searchMovies(
        @Query(value = "s") text: String?,
        @Query(value = "y") year: String,
        @Query(value = "page") page: String,
    ): Response<MoviesSearchResponse>

    @GET("/")
    suspend fun getMovie(
        @Query(value = "i") imdbID: String?,
        @Query(value = "plot") plot: String?,
    ): Response<Movie>
}