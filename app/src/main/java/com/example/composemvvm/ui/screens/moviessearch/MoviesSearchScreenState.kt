package com.example.composemvvm.ui.screens.moviessearch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.network.responses.MoviesSearchResponse
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.flow.MutableStateFlow

class MoviesSearchScreenState : BaseScreenState() {

    val movies = mutableStateListOf<Movie>()
    var searchText = MutableStateFlow("")
    var isLoading by mutableStateOf(false)
    var message: String? by mutableStateOf(null)
    val scroll = ScrollHelper()

    fun handleSearchMovies(source: Source<MoviesSearchResponse>, page: Int) {
        when (source) {
            is Source.Processing -> isLoading = true
            is Source.Success -> source.data?.let {
                message = it.error
                if (page == 1) {
                    movies.clear()
                }
                movies.addAll(source.data.list)
                isLoading = false
                scroll.isLastPage = source.data.list.size < 10
            }

            is Source.Error -> {
                isLoading = false
                context?.showInfoDialog(source.getErrorMessage())
            }
        }
    }
}