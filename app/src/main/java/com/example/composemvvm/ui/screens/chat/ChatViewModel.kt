package com.example.composemvvm.ui.screens.chat

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.composemvvm.core.BaseStateViewModel
import com.example.composemvvm.core.BaseViewModel
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.core.ui.BaseScreenState
import com.example.composemvvm.logget
import com.example.composemvvm.models.Message
import com.example.composemvvm.usecases.GetMessageListUseCase
import com.example.composemvvm.usecases.SendMessageUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    application: Application,
    private val getMessageListUseCase: GetMessageListUseCase,
    private val sendMessageUseCase: SendMessageUseCase,
) : BaseStateViewModel(application) {

    override val uiState = ChatScreenState()

    var messagesState = createSourceMutableLiveData<PaginationSource<Message>>()
        private set

    var updatedMessageState = createSourceMutableLiveData<Message>()
        private set

    private var page = 0

    fun load() {
        messagesState.value = Source.Processing()
        page = 0
        viewModelScope.launch {
            messagesState.value = getMessageListUseCase(page)
        }
    }

    fun loadMore() {
        page++
        viewModelScope.launch {
            messagesState.value = getMessageListUseCase(page)
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            updatedMessageState.value = sendMessageUseCase(message)
//            updatedMessageState.value = Source.Error(Exception("Opss"))
        }
    }
}