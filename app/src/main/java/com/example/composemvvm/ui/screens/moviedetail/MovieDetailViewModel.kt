package com.example.composemvvm.ui.screens.moviedetail

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.usecases.GetMovieUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    application: Application,
    private val getMovieUseCase: GetMovieUseCase
) : BaseStateViewModel(application) {

    override val uiState = MovieDetailScreenState()

    fun loadMovie(imdbID: String?) {
        imdbID ?: return
        viewModelScope.launch {
            uiState.handleMovie(getMovieUseCase(imdbID))
        }
    }
}