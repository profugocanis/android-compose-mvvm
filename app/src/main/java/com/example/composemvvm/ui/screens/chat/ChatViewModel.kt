package com.example.composemvvm.ui.screens.chat

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Message
import com.example.composemvvm.usecases.GetMessageListUseCase
import com.example.composemvvm.usecases.SendMessageUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    application: Application,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseViewModel(application) {

    var messagesState by createSourceMutableState<List<Message>>()
        private set

    var updatedMessageState by createSourceMutableState<Message>()
        private set

    private var page = 0

    init {
        load()
    }

    private fun load() {
        messagesState = Source.Processing()
        page = 0
        viewModelScope.launch {
            messagesState = getMessageListUseCase(page)
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            messagesState = getMessageListUseCase(page)
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            updatedMessageState = sendMessageUseCase(message)
        }
    }
}