package com.example.composemvvm.ui.screens.first

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.composemvvm.core.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.logget
import com.example.composemvvm.models.Product
import com.example.composemvvm.ui.screens.second.SecondScreen
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object FirstScreen : BaseScreen() {

    @Composable
    fun Screen(nav: NavController, viewModel: FirstViewModel = koinViewModel()) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val (loadView, productListView, floatingButton) = createRefs()

            val isLoading = remember { mutableStateOf(false) }
            val isRefreshing = rememberSwipeRefreshState(isRefreshing = false)
            val listState = rememberLazyListState()
            val products = remember { mutableStateOf<List<Product>?>(null) }

            HandleProducts(viewModel.products, products, isLoading)

            AnimatedVisibility(
                visible = products.value != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.constrainAs(productListView) {}
            ) {
                ProductList(nav, viewModel, products, isRefreshing, listState, Modifier)
            }

            AnimatedVisibility(
                visible = isLoading.value,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.constrainAs(loadView) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
            ) {
                CircularProgressIndicator(strokeWidth = 4.dp)
            }

            val scope = rememberCoroutineScope()

            FloatingActionButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                },
                modifier = Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }) {

            }
        }
    }

    @Composable
    private fun ProductList(
        nav: NavController,
        viewModel: FirstViewModel,
        products: MutableState<List<Product>?>,
        swipeRefreshState: SwipeRefreshState,
        listState: LazyListState,
        modifier: Modifier
    ) {

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                logget("Refresh")
                swipeRefreshState.isRefreshing = true
                viewModel.load(false)
            },
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                state = listState,
                modifier = modifier
            ) {
                products.value?.forEach { product ->
                    item() {
                        ProductView(
                            product,
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(PaddingValues(vertical = 4.dp, horizontal = 8.dp))
                                .clickable {
                                    SecondScreen.open(nav, product)
                                }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun HandleProducts(
        source: Source<List<Product>>,
        products: MutableState<List<Product>?>,
        isLoading: MutableState<Boolean>
    ) {
        when (source) {
            is Source.Processing -> isLoading.value = true
            is Source.Success -> {
                products.value = source.data
                isLoading.value = false
            }
            is Source.Error -> {
                ShowError(source.exception)
                isLoading.value = false
            }
        }
    }
}