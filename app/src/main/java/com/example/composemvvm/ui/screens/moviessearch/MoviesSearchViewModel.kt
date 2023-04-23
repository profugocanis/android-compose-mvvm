package com.example.composemvvm.ui.screens.moviessearch

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.usecases.SearchMovieUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MoviesSearchViewModel(
    application: Application,
    private val searchMovieUseCase: SearchMovieUseCase
) : BaseStateViewModel(application) {

    override val uiState = MoviesSearchScreenState()
    private var page = 1
    private var lastSearchText = ""

    init {
        CoroutineScope(Dispatchers.Main).launch {
            uiState.searchText
                .debounce(1_000)
                .distinctUntilChanged()
                .onEach {
                    search(it.trim())
                }.collect()
        }
    }

    private fun search(text: String) {
        if (text.length < 3) return
        lastSearchText = text
        page = 1
        uiState.handleSearchMovies(Source.Processing(), page)
        loadSearch()
    }

    fun loadMore() {
        if (lastSearchText.length < 3) return
        page++
        loadSearch()
    }

    private fun loadSearch() {
        viewModelScope.launch {
            val source = searchMovieUseCase(lastSearchText, page)
            uiState.handleSearchMovies(source, page)
        }
    }
}