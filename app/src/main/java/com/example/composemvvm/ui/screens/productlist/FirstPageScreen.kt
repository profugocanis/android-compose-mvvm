package com.example.composemvvm.ui.screens.productlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.core.ui.PagerItem
import com.example.composemvvm.extentions.CustomLightGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

object FirstPageScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen(nav: NavHostController) {
        Column {
            Content(nav)
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun Content(nav: NavController) {
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()

        val pageTitle = stringResource(id = com.example.composemvvm.R.string.page)
        val items = remember {
            listOf(
                PagerItem("$pageTitle 1"),
                PagerItem("$pageTitle 2"),
                PagerItem("$pageTitle 3"),
            )
        }

        Column {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                        color = MaterialTheme.colors.secondary
                    )
                },
                backgroundColor = Color.CustomLightGray
            ) {
                items.forEachIndexed { index, item ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        text = {
                            Text(
                                text = item.title,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
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
            items[page].Page {
                FirstScreen.Screen(nav = nav, key = it.title)
            }
        }
    }
}