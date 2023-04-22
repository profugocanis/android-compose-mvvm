package com.example.composemvvm.ui.screens.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.extentions.showInfoDialog
import com.example.composemvvm.models.Message
import com.example.composemvvm.utils.ScrollHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatScreenState : BaseScreenState() {

    var isLoading by mutableStateOf(false)
    var isLastPage by mutableStateOf(false)
    val messages = mutableStateListOf<Message>()
    var replayMessage by mutableStateOf<Message?>(null)
    val scroll = ScrollHelper()

    private var coroutineScope: CoroutineScope? = null

    @Composable
    override fun InitComposable() {
        coroutineScope = rememberCoroutineScope()
    }

    fun handleMessages(source: Source<PaginationSource<Message>>, page: Int) {
        when (source) {
            is Source.Processing -> isLoading = true
            is Source.Success -> {
                if (page == 0) {
                    messages.clear()
                }
                addMessages(source.data?.list ?: listOf())
                isLastPage = source.data?.isLastPage ?: true
                isLoading = false
            }

            is Source.Error -> {
                context?.showInfoDialog(source.getErrorMessage())
                isLoading = false
            }
        }
    }

    fun handleUpdateMessage(source: Source<Message>) {
        when (source) {
            is Source.Processing -> Unit
            is Source.Success -> source.data?.let { updateMessage(it) }
            is Source.Error -> context?.showInfoDialog(source.getErrorMessage())
        }
    }

    fun addMessages(message: Message) {
        if (!messages.contains(message)) {
            messages.add(0, message)
        }
        replayMessage = null
        scrollToBottom(50)
    }

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

    fun removeMessage(message: Message) {
        messages.remove(message)
    }

    private fun addMessages(newMessages: List<Message>) {
        if (messages.firstOrNull { newMessages.contains(it) } == null) {
            messages.addAll(newMessages)
        }
    }

    private fun updateMessage(message: Message) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1 && message != messages[index]) {
            messages.removeAt(index)
            messages.add(index, message)
        }
    }
}