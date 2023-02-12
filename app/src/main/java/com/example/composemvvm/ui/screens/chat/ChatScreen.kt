package com.example.composemvvm.ui.screens.chat

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.example.composemvvm.core.Source
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.extentions.CustomBlue
import com.example.composemvvm.extentions.CustomLightGray
import com.example.composemvvm.logget
import com.example.composemvvm.models.Message
import com.example.composemvvm.ui.screens.chat.views.CustomPopMenu
import com.example.composemvvm.ui.screens.chat.views.InputMessageView
import com.example.composemvvm.ui.screens.chat.views.OutputMessageView
import com.example.composemvvm.ui.screens.chat.views.PopMenuItem
import com.example.composemvvm.ui.views.ConstraintLoadView
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

object ChatScreen : BaseScreen() {

    private class ScreenState {
        val isLoading = mutableStateOf(false)
        val scroll = ScrollHelper()
        val messages = mutableStateListOf<Message>()
    }

    fun open(nav: NavController) {
        navigate(nav)
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun Screen(viewModel: ChatViewModel = koinViewModel()) {

        val screenState = viewModel.rememberScreenState { ScreenState() }

        HandleMessages(viewModel.messagesState, screenState)

        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null,
        ) {
            Content(viewModel, screenState)
        }
    }

    @Composable
    private fun Content(viewModel: ChatViewModel, screenState: ScreenState) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            val (inputView, messageListView, loadView) = createRefs()

            if (!screenState.isLoading.value) {
                MessageListView(viewModel, screenState, Modifier.constrainAs(messageListView) {
                    bottom.linkTo(inputView.top, margin = 0.dp)
                    top.linkTo(parent.top, margin = 0.dp)
                    height = Dimension.fillToConstraints
                    width = Dimension.matchParent
                })

                InputView(screenState, modifier = Modifier.constrainAs(inputView) {
                    bottom.linkTo(parent.bottom, margin = 0.dp)
                })
            }

            ConstraintLoadView(screenState.isLoading.value, loadView)
        }
    }

    @Composable
    private fun MessageListView(
        viewModel: ChatViewModel, screenState: ScreenState, modifier: Modifier
    ) {
        LazyColumn(
            state = screenState.scroll.listState,
            contentPadding = PaddingValues(vertical = 4.dp),
            reverseLayout = true,
            modifier = modifier
        ) {
            items(screenState.messages.toList(), key = { it.id }) { message ->
                screenState.scroll.updateScroll()
                val menuItems = remember {
                    listOf(PopMenuItem("Delete") {
                        logget("del ${message.id}")
                    })
                }
                if (message.isInput) {
                    CustomPopMenu(menuItems) { expanded ->
                        InputMessageView(message, onLongClick = {
                            logget(message.id)
                            expanded.value = true
                        })
                    }
                } else {
                    CustomPopMenu(menuItems) { expanded ->
                        OutputMessageView(message, onLongClick = {
                            logget(message.id)
                            expanded.value = true
                        })
                    }
                }
            }

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

    @Composable
    private fun InputView(screenState: ScreenState, modifier: Modifier) {
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
                onValueChange = {
                    text.value = it
                },
                trailingIcon = {
                    Icon(Icons.Filled.Send,
                        contentDescription = "",
                        modifier = Modifier.clickable(role = Role.Button) {
                            val messageText = text.value.text.trim()
                            if (messageText.isEmpty()) return@clickable

                            screenState.messages.add(0, Message(messageText, false))
                            text.value = TextFieldValue("")
                            scope.launch {
                                delay(50)
                                screenState.scroll.listState.animateScrollToItem(0)
                            }
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

    @Composable
    private fun HandleMessages(source: Source<List<Message>>, screenState: ScreenState) {
        when (source) {
            is Source.Processing -> screenState.isLoading.value = true
            is Source.Success -> {
                val list = source.data ?: listOf()
                if (screenState.messages.firstOrNull { list.contains(it) } == null) {
                    screenState.messages.addAll(list)
                }
                screenState.isLoading.value = false
            }
            is Source.Error -> {
                ShowError(source.exception)
                screenState.isLoading.value = false
            }
        }
    }
}