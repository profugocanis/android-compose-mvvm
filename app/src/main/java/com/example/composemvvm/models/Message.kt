package com.example.composemvvm.models

import java.util.*

data class Message(
    val text: String? = null,
    var isSend: Boolean = true,
    val isInput: Boolean = Random().nextBoolean(),
    val id: String = UUID.randomUUID().toString(),
)