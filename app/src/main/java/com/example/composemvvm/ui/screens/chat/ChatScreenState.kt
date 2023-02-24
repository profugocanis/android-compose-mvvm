package com.example.composemvvm.ui.screens.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.composemvvm.core.ui.ScreenState
import com.example.composemvvm.models.Message
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatScreenState(val scope: CoroutineScope): ScreenState {

    val isLoading = mutableStateOf(false)
    val isLastPage = mutableStateOf(false)
    val scroll = ScrollHelper()
    val messages = mutableStateListOf<Message>()
    val replayMessage = mutableStateOf<Message?>(null)

    fun scrollToBottom(delayTime: Long = 0) {
        scope.launch {
            if (delayTime != 0L) {
                delay(delayTime)
            }
            scroll.listState.animateScrollToItem(0)
        }
    }

    fun addMessages(newMessages: List<Message>) {
        if (messages.firstOrNull { newMessages.contains(it) } == null) {
            messages.addAll(newMessages)
        }
    }

    fun addMessages(message: Message) {
        if (!messages.contains(message)) {
            messages.add(0, message)
        }
        scrollToBottom(50)
    }

    fun updateMessage(message: Message) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1 && message != messages[index]) {
            messages.removeAt(index)
            messages.add(index, message)
        }
    }
}