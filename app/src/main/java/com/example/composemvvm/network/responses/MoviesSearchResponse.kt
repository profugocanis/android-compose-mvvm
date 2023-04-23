package com.example.composemvvm.network.responses

import com.example.composemvvm.models.movies.Movie
import com.google.gson.annotations.SerializedName

data class MoviesSearchResponse(
    @SerializedName("Search") val list: List<Movie> = emptyList(),
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val response: String? = null,
    @SerializedName("Error") val error: String? = null,
)