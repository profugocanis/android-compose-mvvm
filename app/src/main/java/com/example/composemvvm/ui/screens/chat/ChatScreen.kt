package com.example.composemvvm.ui.screens.chat

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.logget
import com.example.composemvvm.models.Message
import com.example.composemvvm.ui.screens.chat.views.*
import com.example.composemvvm.ui.views.ConstraintLoadView
import com.example.composemvvm.ui.views.PopMenuItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object ChatScreen : BaseScreen() {

    fun open(nav: NavController) {
        navigate(nav)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Screen(viewModel: ChatViewModel = koinViewModel()) {
        logget("ChatScreen update")

        val screenState = viewModel.rememberScreenState { ChatScreenState() }

        HandleMessages(viewModel.messagesState, screenState)
        HandleUpdateMessage(viewModel.updatedMessageState, screenState)

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
                    bottom.linkTo(inputView.top, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
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
                screenState.scroll.updateScroll()
                MessageView(
                    message = message,
                    message.isInput,
                    modifier = Modifier.animateItemPlacement(),
                    menuItems = listOf(PopMenuItem("Delete") {
                        removeMessage(message, screenState)
                    })
                )
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
    private fun InputView(
        viewModel: ChatViewModel, screenState: ChatScreenState, modifier: Modifier
    ) {
        val text = remember { mutableStateOf(TextFieldValue("")) }
        val scope = rememberCoroutineScope()
        Column(modifier = modifier.fillMaxWidth()) {
            Divider()

            TextField(
                value = text.value,
                maxLines = 3,
                placeholder = { Text(text = "Enter text") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                onValueChange = { text.value = it },
                trailingIcon = {
                    Icon(Icons.Filled.Send,
                        contentDescription = "",
                        modifier = Modifier.clickable(role = Role.Button) {
                            sendMessage(text.value.text.trim(), screenState, viewModel, scope)
                            text.value = TextFieldValue("")
                        })
                },
                shape = CircleShape,
                colors = TextFieldDefaults.textFieldColors(
                    trailingIconColor = Color.CustomBlue,
                    backgroundColor = Color.CustomLightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun ButtonScrollStart(screenState: ChatScreenState, modifier: Modifier) {
        AnimatedVisibility(
            visible = screenState.scroll.isShowFloating.value,
            enter = scaleIn(animationSpec = tween(300)),
            exit = scaleOut(animationSpec = tween(300)),
            modifier = modifier
        ) {
            val scope = rememberCoroutineScope()
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        screenState.scroll.listState.animateScrollToItem(0)
                    }
                },
                backgroundColor = Color.CustomBlue,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(Icons.Filled.KeyboardArrowDown, "menu", tint = Color.White)
            }
        }
    }

    private fun sendMessage(
        text: String, screenState: ChatScreenState, viewModel: ChatViewModel, scope: CoroutineScope
    ) {
        if (text.isEmpty()) return
        val message = Message(text = text, isSend = false, isInput = false)
        screenState.addMessages(message)
        viewModel.sendMessage(message)
        scope.launch {
            delay(50)
            screenState.scroll.listState.animateScrollToItem(0)
        }
    }

    private fun removeMessage(message: Message, screenState: ChatScreenState) {
        screenState.messages.remove(message)
    }

    @Composable
    private fun HandleMessages(
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
                ShowError(source.exception)
                screenState.isLoading.value = false
            }
        }
    }

    @Composable
    private fun HandleUpdateMessage(source: Source<Message>, screenState: ChatScreenState) {
        when (source) {
            is Source.Processing -> Unit
            is Source.Success -> source.data?.let { screenState.updateMessage(it) }
            is Source.Error -> {
                ShowError(source.exception)
            }
        }
    }
}