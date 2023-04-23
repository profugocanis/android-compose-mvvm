package com.example.composemvvm.ui.screens.moviessearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.ui.screens.moviedetail.MovieDetailScreen
import com.example.composemvvm.ui.screens.moviessearch.views.MovieView
import com.example.composemvvm.ui.views.HeaderView
import org.koin.androidx.compose.koinViewModel

object MoviesSearchScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController, viewModel: MoviesSearchViewModel = koinViewModel()) {
        val screenState: MoviesSearchScreenState = viewModel.getState()

        onCreate {
            screenState.searchText.value = "one"
        }
        Content(nav, viewModel, screenState)
    }

    @Composable
    private fun Content(
        nav: NavController,
        viewModel: MoviesSearchViewModel,
        screenState: MoviesSearchScreenState
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeaderView("OMDd search")
                SearchTextField(screenState)
                SearchResultList(nav, viewModel, screenState)
            }

            if (screenState.message?.isNotEmpty() == true) {
                Text(text = screenState.message ?: "")
            }

            if (screenState.isLoading) {
                CircularProgressIndicator(strokeWidth = 4.dp)
            }
        }
    }

    @Composable
    private fun SearchTextField(screenState: MoviesSearchScreenState) {
        OutlinedTextField(value = screenState.searchText.collectAsState().value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            label = {
                Text("Search")
            },
            onValueChange = {
                screenState.searchText.value = it
            })
    }

    @Composable
    private fun SearchResultList(
        nav: NavController,
        viewModel: MoviesSearchViewModel,
        screenState: MoviesSearchScreenState
    ) {
        val viewWidth = (LocalConfiguration.current.screenWidthDp).dp / 2
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = viewWidth)) {

            items(screenState.movies, key = { it.imdbID ?: "" }) {
                screenState.scroll.updateScroll()
                MovieView(it, modifier = Modifier.onBounceClick {
                    MovieDetailScreen.open(nav, it)
                })
            }

            item {
                if (!screenState.scroll.isLastPage) {
                    onResume {
                        viewModel.loadMore()
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp, modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}