package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay

class SendMessageUseCase : BaseUseCase() {

    suspend operator fun invoke(message: Message): Source<Message> {
        delay(500)
        message.isSend = true
        return Source.Success(message)
    }
}