package com.example.composemvvm.ui.screens.first

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.onBounceClick
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object FirstScreen : BaseScreen() {

    @Composable
    fun Screen(
        nav: NavController,
        key: String,
        viewModel: FirstViewModel = koinViewModel(key = key)
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val screenState: FirstScreenState = viewModel.getState()

            val (floatingButton) = createRefs()

            screenState.scroll.refreshing.isRefreshing = false

            ProductList(nav, viewModel, screenState)

            AnimatedVisibility(visible = screenState.scroll.isShowFloating,
                enter = slideInVertically { 300 },
                exit = slideOutVertically { 300 },
                modifier = Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }) {
                val scope = rememberCoroutineScope()
                FloatingActionButton(onClick = {
                    scope.launch {
                        screenState.scroll.listState.animateScrollToItem(0)
                    }
                }) {
                    Icon(Icons.Filled.KeyboardArrowUp, "menu", tint = Color.White)
                }
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ProductList(
        nav: NavController,
        viewModel: FirstViewModel,
        screenState: FirstScreenState,
    ) {
        SwipeRefresh(
            state = screenState.scroll.refreshing, onRefresh = {
                screenState.scroll.refreshing.isRefreshing = true
                viewModel.load(false)
            }, modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                state = screenState.scroll.listState,
                contentPadding = PaddingValues(top = 4.dp, bottom = 4.dp),
                userScrollEnabled = screenState.scroll.isScrollEnable.value
            ) {

                items(screenState.products, key = { it.uuid }) {
                    screenState.scroll.updateScroll()
                    ProductView(it,
                        modifier = Modifier
                            .animateItemPlacement()
                            .fillParentMaxWidth()
                            .padding(PaddingValues(vertical = 8.dp, horizontal = 8.dp))
                            .onBounceClick {
                                SecondScreen.open(nav, it)
                            })
                }

                item {
                    if (!screenState.scroll.isLastPage) {
                        onResume {
                            viewModel.loadMore()
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
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
}