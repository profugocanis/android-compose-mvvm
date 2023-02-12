package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay

class GetMessageListUseCase : BaseUseCase() {

    private val pageLimit = 20

    suspend operator fun invoke(page: Int): Source<PaginationSource<Message>> {
        delay(500)
        var messages =
            (page * pageLimit until page * pageLimit + pageLimit).map { Message("Message $it") }
        if (page >= 1) {
            messages = messages.reversed().takeLast(pageLimit / 6)
        }
        return Source.Success(
            PaginationSource(list = messages, pageSize = messages.size, pageLimit = pageLimit)
        )
    }
}