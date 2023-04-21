package com.example.composemvvm.ui.screens.chat

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.composemvvm.core.ui.ScreenState
import com.example.composemvvm.models.Message
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatScreenState : ScreenState {

    var coroutineScope: CoroutineScope? = null
    var context: Context? = null

    val isLoading = mutableStateOf(false)
    val isLastPage = mutableStateOf(false)
    val scroll = ScrollHelper()
    val messages = mutableStateListOf<Message>()
    val replayMessage = mutableStateOf<Message?>(null)

    fun scrollToMessage(message: Message) {
        val index = messages.indexOf(message)
        if (index == -1) return
        coroutineScope?.launch {
            scroll.listState.animateScrollToItem(index)
        }
    }

    fun scrollToBottom(delayTime: Long = 0) {
        coroutineScope?.launch {
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
        replayMessage.value = null
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