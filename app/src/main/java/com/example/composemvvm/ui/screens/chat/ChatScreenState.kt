package com.example.composemvvm.ui.screens.chat

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.composemvvm.models.Message
import com.example.composemvvm.utils.ScrollHelper

class ChatScreenState {
    val isLoading = mutableStateOf(false)
    val scroll = ScrollHelper()
    val messages = mutableStateListOf<Message>()

    fun addMessages(newMessages: List<Message>) {
        if (messages.firstOrNull { newMessages.contains(it) } == null) {
            messages.addAll(newMessages)
        }
    }

    fun addMessages(message: Message) {
        if (!messages.contains(message)) {
            messages.add(0, message)
        }
    }

    fun updateMessage(message: Message) {
        val index = messages.indexOfFirst { it.id == message.id }
        if (index != -1) {
            messages.removeAt(index)
            messages.add(index, message)
        }
    }
}