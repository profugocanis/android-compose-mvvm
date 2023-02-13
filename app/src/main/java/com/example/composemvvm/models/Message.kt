package com.example.composemvvm.models

import java.util.*

data class Message(
    val data: MessageData? = null,
    var isSend: Boolean = true,
    val date: Date = Date(),
    val isInput: Boolean = Random().nextBoolean(),
    val id: String = UUID.randomUUID().toString(),
) {

    fun <T> getData(): T {
        return data as T
    }
}