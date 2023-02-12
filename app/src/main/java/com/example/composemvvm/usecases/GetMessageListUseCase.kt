package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.Message
import kotlinx.coroutines.delay
import java.util.*

class GetMessageListUseCase : BaseUseCase() {

    private val pageLimit = 20

    suspend operator fun invoke(page: Int): Source<PaginationSource<Message>> {
        delay(500)

        val messages = listOf(
            Message(
                text = "You need to sort dates, not strings. Also, have you heared about DateFormat? It makes all that appends for you.",
                isInput = false,
                date = Date(Date().time - 24 * 60 * 60 * 1_000)
            ),
            Message(
                text = "this format is equal to",
                isInput = true,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            ),
            Message(
                text = "here is the example for date format",
                isInput = false,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            )
        )

        return Source.Success(
            PaginationSource(list = messages, pageSize = messages.size, pageLimit = pageLimit)
        )

//        var messages =
//            (page * pageLimit until page * pageLimit + pageLimit).map { Message("Message $it") }
//        if (page >= 1)
//            messages = messages.reversed().takeLast(pageLimit / 10)
//
//        return Source.Success(
//            PaginationSource(list = messages, pageSize = messages.size, pageLimit = pageLimit)
//        )
    }
}