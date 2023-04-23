package com.example.composemvvm.usecases

import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay

class SendMessageUseCase : BaseUseCase() {

    suspend operator fun invoke(message: Message): Source<Message> {
        delay(500)
//        message.isSend = true
        val updatedMessage = message.copy()//Message(message.id, message.isSend)
        updatedMessage.isSend = true
        return Source.Success(updatedMessage)
    }
}