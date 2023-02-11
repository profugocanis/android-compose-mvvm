package com.example.composemvvm.ui.screens.first

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.Main
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object FirstPageScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    private class ItemRow(val title: String)

    private val items = listOf(ItemRow("Screen 1"), ItemRow("Screen 2"))

    @Composable
    fun Screen(nav: NavHostController, navBackStackEntry: NavBackStackEntry) {

        Column {
            Content(nav)
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun Content(nav: NavController) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        Column() {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = MaterialTheme.colors.secondary
                    )
                },
                backgroundColor = Color.Main
            ) {
                items.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        icon = {
//                            Icon(imageVector = item.icon, contentDescription = "")
                        },
                        text = {
                            Text(
                                text = item.title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.height(32.dp)
                            )
                        },
                        modifier = Modifier
                    )
                }
            }
        }

        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> FirstScreen.Screen(nav = nav, viewModel = koinViewModel(key = "s1"))
                1 -> FirstScreen.Screen(nav = nav, viewModel = koinViewModel(key = "s2"))
            }
        }
    }
}