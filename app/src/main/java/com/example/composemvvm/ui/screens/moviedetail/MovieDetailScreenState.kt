package com.example.composemvvm.ui.screens.moviedetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.utils.ScrollHelper

class MovieDetailScreenState : BaseScreenState() {

    var movie by mutableStateOf<Movie?>(null)
    var isLoading by mutableStateOf(false)
    val scroll = ScrollHelper()

    fun handleMovie(source: Source<Movie>) {
        when (source) {
            is Source.Processing -> isLoading = true
            is Source.Success -> movie = source.data
            is Source.Error -> {
                isLoading = false
                context?.showInfoDialog(source.getErrorMessage())
            }
        }
    }
}