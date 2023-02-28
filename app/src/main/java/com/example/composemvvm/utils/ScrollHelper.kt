package com.example.composemvvm.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.mutableStateOf
import com.google.accompanist.swiperefresh.SwipeRefreshState

class ScrollHelper {

    val isShowFloating = mutableStateOf(false)
    val isLastPage = mutableStateOf(false)
    val refreshing = SwipeRefreshState(isRefreshing = false)
    val listState = LazyListState()
    val isScrollEnable = mutableStateOf(false)

    fun updateScroll(visibleItem: Int = 0) {
        isShowFloating.value = listState.firstVisibleItemIndex > visibleItem
    }
}