package com.example.composemvvm.ui.screens.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.composemvvm.core.NavigationArguments
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.getObject
import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.ui.screens.moviessearch.views.getHeightByWidth
import org.koin.androidx.compose.koinViewModel

object MovieDetailScreen : BaseScreen() {

    private const val MOVIE_KEY = "MOVIE_KEY"

    override val arguments = NavigationArguments()
        .registerString(MOVIE_KEY)

    fun open(nav: NavController, movie: Movie) {
        val args = mapOf(
            MOVIE_KEY to movie
        )
        navigate(nav, args)
    }

    @Composable
    fun Screen(stackEntry: NavBackStackEntry?) {
        val movie = stackEntry?.getObject<Movie>(MOVIE_KEY)
        if (movie != null) {
            Screen(movie = movie)
        }
    }

    @Composable
    fun Screen(movie: Movie, viewModel: MovieDetailViewModel = koinViewModel()) {
        val state: MovieDetailScreenState = viewModel.getState()
        if (state.movie?.imdbID != movie.imdbID) {
            state.movie = movie
            viewModel.loadMovie(state.movie?.imdbID)
        }

        Content(state)
    }

    @Composable
    private fun Content(screenState: MovieDetailScreenState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(screenState.movie?.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(LocalConfiguration.current.screenWidthDp.dp)
                    .height(getHeightByWidth(LocalConfiguration.current.screenWidthDp.dp))
            )

            Text(text = screenState.movie?.title ?: "", fontSize = 24.sp)
            Text(text = screenState.movie?.plot ?: "")
        }
    }
}