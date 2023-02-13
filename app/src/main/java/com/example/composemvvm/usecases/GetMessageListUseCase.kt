package com.example.composemvvm.usecases

import com.example.composemvvm.core.BaseUseCase
import com.example.composemvvm.core.network.PaginationSource
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.models.Message
import com.example.composemvvm.models.MessageData
import kotlinx.coroutines.delay
import java.util.*

class GetMessageListUseCase : BaseUseCase() {

    private val pageLimit = 20

    suspend operator fun invoke(page: Int): Source<PaginationSource<Message>> {
        delay(500)

        val messages = mutableListOf(
            Message(
                data = MessageData.Text("You need to sort dates, not strings. Also, have you heared about DateFormat? It makes all that appends for you."),
                isInput = false,
                date = Date(Date().time - 24 * 60 * 60 * 1_000)
            ),
            Message(
                data = MessageData.Image("https://media.istockphoto.com/id/1322277517/photo/wild-grass-in-the-mountains-at-sunset.jpg?s=612x612&w=0&k=20&c=6mItwwFFGqKNKEAzv0mv6TaxhLN3zSE43bWmFN--J5w="),
                isInput = false,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            ),
            Message(
                data = MessageData.Image("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8aHVtYW58ZW58MHx8MHx8&w=1000&q=80"),
                isInput = true,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            ),
            Message(
                data = MessageData.Text("this format is equal to"),
                isInput = true,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            ),
            Message(
                data = MessageData.Text("here is the example for date format"),
                isInput = false,
                date = Date(Date().time - 2 * 24 * 60 * 60 * 1_000)
            )
        )

        if (page > 0) {
            messages.clear()
        }

        var randomMessages =
            (page * pageLimit until page * pageLimit + pageLimit).map { Message(MessageData.Text("Message $it")) }
        if (page >= 1)
            randomMessages = randomMessages.reversed().takeLast(pageLimit / 10)

        messages.addAll(randomMessages)
        return Source.Success(
            PaginationSource(list = messages, pageSize = messages.size, pageLimit = pageLimit)
        )
    }
}