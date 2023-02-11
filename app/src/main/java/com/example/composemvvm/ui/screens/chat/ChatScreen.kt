package com.example.composemvvm.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.example.composemvvm.core.ui.BaseScreen
import com.example.composemvvm.logget
import com.example.composemvvm.models.Message
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.launch

object ChatScreen : BaseScreen() {

    private class ScreenState {
        val isLoading = mutableStateOf(false)
        val scroll = ScrollHelper()
        var messages = mutableStateListOf<Message>()

        init {
            val list = (0..50).map { Message("Message $it") }.toMutableList()
            messages.addAll(list)
        }
    }

    private val screenState = ScreenState()

    fun open(nav: NavController) {
        navigate(nav)
    }

    @Composable
    fun Screen() {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (inputView, messageListView) = createRefs()

//            val scope = rememberCoroutineScope()
//            onResume {
//                scope.launch {
//                    state.scroll.listState.scrollToItem(Int.MAX_VALUE)
//                }
//            }

            MessageListView(Modifier.constrainAs(messageListView) {
                bottom.linkTo(inputView.top, margin = 0.dp)
                top.linkTo(parent.top, margin = 0.dp)
                height = Dimension.fillToConstraints
                width = Dimension.matchParent
            })
            InputView(modifier = Modifier.constrainAs(inputView) {
                bottom.linkTo(parent.bottom, margin = 0.dp)
            })
        }
    }

    @Composable
    private fun MessageListView(modifier: Modifier) {
        LazyColumn(
            state = screenState.scroll.listState,
            contentPadding = PaddingValues(vertical = 4.dp),
            reverseLayout = true,
            modifier = modifier
        ) {
            items(screenState.messages.reversed(), key = { it.id }) {
                screenState.scroll.updateScroll()
                Text(text = it.id, fontSize = 9.sp, color = Color.Gray)
                Text(text = it.text.toString())
            }

            item {
                onResume {
                    logget("Load more")
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
    private fun InputView(modifier: Modifier) {
        val text = remember { mutableStateOf(TextFieldValue("")) }
        val scope = rememberCoroutineScope()
        Row(modifier = modifier.fillMaxWidth()) {
            TextField(
                value = text.value,
                placeholder = { Text(text = "Enter text") },
                modifier = Modifier, onValueChange = {
                    text.value = it
                })

            Button(onClick = {
                screenState.messages.add(Message(text.value.text))
                text.value = TextFieldValue("")
                scope.launch {
                    screenState.scroll.listState.animateScrollToItem(0)
                }
            }) {
                Text(text = "Send")
            }
        }
    }
}