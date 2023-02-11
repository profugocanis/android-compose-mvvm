package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay

class GetMessageListUseCase : BaseUseCase() {

    suspend operator fun invoke(): Source<List<Message>> {
        delay(500)
        val messages = (0..50).map { Message("Message $it") }
        return Source.Success(messages)
    }
}