package com.example.composemvvm.ui.screens.chat

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.ui.screens.chat.views.*
import com.example.composemvvm.ui.views.ConstraintLoadView
import com.example.composemvvm.ui.views.PopMenuItem
import org.koin.androidx.compose.koinViewModel

object ChatScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Screen(viewModel: ChatViewModel = koinViewModel()) {
        val screenState: ChatScreenState = viewModel.getState()
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
        ) {
            Content(viewModel, screenState)
        }
    }

    @Composable
    private fun Content(viewModel: ChatViewModel, screenState: ChatScreenState) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (inputView, messageListView, loadView, floatingButton) = createRefs()

            if (!screenState.isLoading) {
                MessageListView(viewModel, screenState, Modifier.constrainAs(messageListView) {
                    bottom.linkTo(inputView.top, margin = 0.dp)
                    top.linkTo(parent.top, margin = 0.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.matchParent
                })

                ButtonScroll(screenState, Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(inputView.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                })

                InputView(screenState, modifier = Modifier.constrainAs(inputView) {
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                }, onSend = {
                    viewModel.sendMessage(it)
                })
            }

            ConstraintLoadView(screenState.isLoading, loadView)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun MessageListView(
        viewModel: ChatViewModel, screenState: ChatScreenState, modifier: Modifier
    ) {
        LazyColumn(
            state = screenState.scroll.listState,
            contentPadding = PaddingValues(vertical = 4.dp),
            reverseLayout = true,
            modifier = modifier
        ) {
            items(screenState.messages.toList(), key = { it.id }) { message ->
                screenState.scroll.updateScroll(1)

                val menuItems = listOf(
                    PopMenuItem("Replay") {
                        screenState.replayMessage = message
                    },
                    PopMenuItem("Delete", color = Color.Red) {
                        screenState.removeMessage(message)
                    })

                MessageView(
                    message = message,
                    menuItems = menuItems,
                    modifier = Modifier.animateItemPlacement(),
                    onReplayTap = {
                        screenState.scrollToMessage(it)
                    }
                )

                DateView(message, screenState.messages, Modifier.animateItemPlacement())
            }

            if (!screenState.isLastPage) {
                item {
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

    @Composable
    private fun ButtonScroll(screenState: ChatScreenState, modifier: Modifier) {
        AnimatedVisibility(
            visible = screenState.scroll.isShowFloating,
            enter = scaleIn(animationSpec = tween(200)),
            exit = scaleOut(animationSpec = tween(200)),
            modifier = modifier
        ) {
            FloatingActionButton(
                onClick = {
                    screenState.scrollToBottom()
                }, backgroundColor = Color.CustomBlue, modifier = Modifier.size(36.dp)
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "menu", tint = Color.White)
            }
        }
    }
}