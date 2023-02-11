package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay

class GetMessageListUseCase : BaseUseCase() {

    private val pageSize = 20

    suspend operator fun invoke(page: Int): Source<List<Message>> {
        delay(500)
        val messages = (page * pageSize until page * pageSize + pageSize).map { Message("Message $it") }
        return Source.Success(messages)
    }
}