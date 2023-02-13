package com.example.composemvvm.models

sealed class MessageData {
    data class Text(val text: String? = null) : MessageData()
    data class Image(val url: String? = null) : MessageData()
}