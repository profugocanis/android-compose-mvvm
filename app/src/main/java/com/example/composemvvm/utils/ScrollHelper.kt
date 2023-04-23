package com.example.composemvvm.utils

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.accompanist.swiperefresh.SwipeRefreshState

class ScrollHelper {

    var isShowFloating by mutableStateOf(false)
    var isLastPage by mutableStateOf(true)
    val refreshing = SwipeRefreshState(isRefreshing = false)
    val listState = LazyListState()
    val isScrollEnable = mutableStateOf(false)

    fun updateScroll(visibleItem: Int = 0) {
        isShowFloating = listState.firstVisibleItemIndex > visibleItem
    }
}