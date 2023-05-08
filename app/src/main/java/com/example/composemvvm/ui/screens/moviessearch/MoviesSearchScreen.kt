package com.example.composemvvm.ui.screens.moviessearch

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composemvvm.R
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomGray
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.models.movies.Movie
import com.example.composemvvm.ui.screens.moviedetail.MovieDetailScreen
import com.example.composemvvm.ui.screens.moviessearch.views.MovieView
import com.example.composemvvm.ui.views.HeaderView
import com.example.composemvvm.utils.CustomToast
import com.mxalbert.sharedelements.FadeMode
import com.mxalbert.sharedelements.MaterialContainerTransformSpec
import com.mxalbert.sharedelements.SharedElementsRoot
import com.mxalbert.sharedelements.SharedMaterialContainer
import org.koin.androidx.compose.koinViewModel

object MoviesSearchScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController, viewModel: MoviesSearchViewModel = koinViewModel()) {
        val screenState: MoviesSearchScreenState = viewModel.getState()
        onCreate {
            screenState.searchText.value = "one"
        }
        BackHandler(enabled = screenState.selectedMovie != null) {
            screenState.selectedMovie = null
        }
        SharedElementsRoot {
            Content(nav, viewModel, screenState)

            if (screenState.selectedMovie != null) {
                val movie = screenState.selectedMovie ?: return@SharedElementsRoot
                SharedMaterialContainer(
                    key = movie.imdbID ?: "",
                    screenKey = "DetailsScreen",
                    isFullscreen = true,
                    transitionSpec = FadeOutTransitionSpec
                ) {
                    MovieDetailScreen.Screen(movie)
                }
            }
        }
    }

    @Composable
    private fun Content(
        nav: NavController,
        viewModel: MoviesSearchViewModel,
        screenState: MoviesSearchScreenState
    ) {
        val activity = LocalContext.current as Activity
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .background(Color.CustomLightGray)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeaderView("OMDd search", onBack = {
                    activity.finish()
                })
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
        TextField(
            value = screenState.searchText.collectAsState().value,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            onValueChange = {
                screenState.searchText.value = it
            },
            placeholder = {
                Text("Search")
            },
            singleLine = false,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(72.dp),
            colors = TextFieldDefaults.textFieldColors(
                leadingIconColor = Color.CustomBlue,
                trailingIconColor = Color.CustomBlue,
                backgroundColor = Color.CustomGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }

    @Composable
    private fun SearchResultList(
        nav: NavController,
        viewModel: MoviesSearchViewModel,
        screenState: MoviesSearchScreenState
    ) {
        val viewWidth = (LocalConfiguration.current.screenWidthDp).dp / 2
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = viewWidth),
            modifier = Modifier
                .padding(top = 8.dp)
        ) {

            items(screenState.movies, key = { it.imdbID ?: "" }) {
                screenState.scroll.updateScroll()
                GetSharedMovieView(it, screenState)
//                MovieView(it, modifier = Modifier.onBounceClick {
//                    MovieDetailScreen.open(nav, it)
//                })
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

    @Composable
    private fun GetSharedMovieView(movie: Movie, screenState: MoviesSearchScreenState) {
        if (screenState.selectedMovie?.imdbID != movie.imdbID) {
            SharedMaterialContainer(
                key = movie.imdbID ?: "",
                screenKey = "MovieView",
                transitionSpec = FadeOutTransitionSpec,
                color = Color.CustomLightGray
            ) {
                MovieView(movie, modifier = Modifier.onBounceClick {
                    screenState.selectedMovie = movie
                })
            }
        }
    }
}

private val FadeOutTransitionSpec = MaterialContainerTransformSpec(
    durationMillis = 300,
    fadeMode = FadeMode.In
)