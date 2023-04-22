package com.example.composemvvm.ui.screens.chat

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import com.example.composemvvm.usecases.GetMessageListUseCase
import com.example.composemvvm.usecases.SendMessageUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    application: Application,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseStateViewModel(application) {

    override val uiState = ChatScreenState()

    private var page = 0

    init {
        load()
    }

    private fun load() {
        uiState.handleMessages(Source.Processing(), page)
        page = 0
        viewModelScope.launch {
            uiState.handleMessages(getMessageListUseCase(page), page)
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            uiState.handleMessages(getMessageListUseCase(page), page)
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            uiState.handleUpdateMessage(sendMessageUseCase(message))
//            uiState.handleUpdateMessage(Source.Error(Exception("Opss")))
        }
    }

    fun sendMessage(messageData: MessageData) {
        val message = Message(
            data = messageData,
            replayedMessage = uiState.replayMessage,
            isSend = false,
            isInput = false
        )
        uiState.addMessages(message)
        viewModelScope.launch {
            uiState.handleUpdateMessage(sendMessageUseCase(message))
//            uiState.handleUpdateMessage(Source.Error(Exception("Opss")))
        }
    }
}