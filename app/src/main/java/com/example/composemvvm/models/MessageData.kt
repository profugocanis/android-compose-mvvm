package com.example.composemvvm.models

import android.graphics.Bitmap

sealed class MessageData {
    data class Text(val text: String? = null) : MessageData()
    data class Image(val url: String? = null, val bitmap: Bitmap? = null) : MessageData()
}