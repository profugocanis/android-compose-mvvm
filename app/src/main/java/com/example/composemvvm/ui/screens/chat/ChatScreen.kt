package com.example.composemvvm.ui.screens.chat

import android.graphics.Bitmap
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.logget
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
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

        onCreate { owner ->
            viewModel.updatedMessageState.observe(owner) { handleUpdateMessage(it, screenState) }
            viewModel.messagesState.observe(owner) { handleMessages(it, screenState) }
            viewModel.load()
        }

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

            if (!screenState.isLoading.value) {
                MessageListView(viewModel, screenState, Modifier.constrainAs(messageListView) {
                    bottom.linkTo(inputView.top, margin = 0.dp)
                    top.linkTo(parent.top, margin = 0.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.matchParent
                })

                ButtonScrollStart(screenState, Modifier.constrainAs(floatingButton) {
                    bottom.linkTo(inputView.top, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                })

                InputView(viewModel, screenState, modifier = Modifier.constrainAs(inputView) {
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                })
            }

            ConstraintLoadView(screenState.isLoading.value, loadView)
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
                        screenState.replayMessage.value = message
                    },
                    PopMenuItem("Delete", color = Color.Red) {
                        removeMessage(message, screenState)
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

            if (!screenState.isLastPage.value) {
                item {
                    onResume {
                        logget("Load more")
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
    private fun ButtonScrollStart(screenState: ChatScreenState, modifier: Modifier) {
        AnimatedVisibility(
            visible = screenState.scroll.isShowFloating.value,
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

    fun sendMessage(text: String, screenState: ChatScreenState, viewModel: ChatViewModel) {
        if (text.isEmpty()) return
        val message = Message(
            data = MessageData.Text(text),
            replayedMessage = screenState.replayMessage.value,
            isSend = false,
            isInput = false
        )
        screenState.addMessages(message)
        viewModel.sendMessage(message)
    }

    fun sendImage(bitmap: Bitmap?, screenState: ChatScreenState, viewModel: ChatViewModel) {
        val messageData = MessageData.Image(bitmap = bitmap)
        val message = Message(
            data = messageData,
            replayedMessage = screenState.replayMessage.value,
            isSend = false,
            isInput = false
        )
        screenState.addMessages(message)
        viewModel.sendMessage(message)
    }

    private fun removeMessage(message: Message, screenState: ChatScreenState) {
        screenState.messages.remove(message)
    }

    private fun handleMessages(
        source: Source<PaginationSource<Message>>,
        screenState: ChatScreenState
    ) {
        when (source) {
            is Source.Processing -> screenState.isLoading.value = true
            is Source.Success -> {
                screenState.addMessages(source.data?.list ?: listOf())
                screenState.isLastPage.value = source.data?.isLastPage ?: true
                screenState.isLoading.value = false
            }

            is Source.Error -> {
                screenState.context?.showInfoDialog(source.getErrorMessage())
                screenState.isLoading.value = false
            }
        }
    }

    private fun handleUpdateMessage(source: Source<Message>, screenState: ChatScreenState) {
        when (source) {
            is Source.Processing -> Unit
            is Source.Success -> source.data?.let { screenState.updateMessage(it) }
            is Source.Error -> {
                screenState.context?.showInfoDialog(source.getErrorMessage())
            }
        }
    }
}