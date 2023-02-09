package com.example.composemvvm.ui.screens.first

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (loadView, productListView, floatingButton) = createRefs()
            val isShowFloating = remember { mutableStateOf(false) }
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
                ProductList(
                    nav,
                    viewModel,
                    products,
                    isRefreshing,
                    isShowFloating,
                    listState
                )
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

            AnimatedVisibility(
                visible = isShowFloating.value,
                enter = slideInVertically { 300 },
                exit = slideOutVertically { 300 },
                modifier = Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                }
            ) {
                val scope = rememberCoroutineScope()
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }) {
                    Icon(Icons.Filled.KeyboardArrowUp, "menu", tint = Color.White)
                }
            }
        }
    }

    @Composable
    private fun ProductList(
        nav: NavController,
        viewModel: FirstViewModel,
        products: MutableState<List<Product>?>,
        swipeRefreshState: SwipeRefreshState,
        isShowFloating: MutableState<Boolean>,
        listState: LazyListState,
    ) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                logget("Refresh")
                swipeRefreshState.isRefreshing = true
                viewModel.load(false)
            },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(state = listState) {
                products.value?.forEach { product ->
                    isShowFloating.value = listState.firstVisibleItemIndex > 0
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