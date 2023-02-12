package com.example.composemvvm.core.network

sealed class Source<out DATA : Any> {
    data class Processing(val msg: String? = null) : Source<Nothing>()
    data class Success<out DATA : Any>(val data: DATA? = null) : Source<DATA>()
    data class Error(val exception: Throwable) : Source<Nothing>() {
        fun getErrorMessage(): String? {
            return exception.message
        }
    }

    val successObj: DATA? get() = if (this is Success) data else null
}