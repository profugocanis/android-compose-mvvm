package com.example.composemvvm.network

import com.example.composemvvm.core.network.BaseRemoteDataSource
import com.example.composemvvm.core.network.Source
import com.google.gson.JsonElement
import com.google.gson.stream.MalformedJsonException
import org.koin.core.component.KoinComponent

class RemoteDataSource : BaseRemoteDataSource(), KoinComponent {

    override fun handleServerError(jsonError: JsonElement?, errorCode: Int): Source.Error {
        return when (errorCode) {
            401 -> {
                showLogin()
                Source.Error(Exception())
            }

            426 -> {
                cancelChildrenRequests()
                showNewVersionDialog()
                Source.Error(RuntimeException("Old version"))
            }

            in 500..599 -> {
                Source.Error(Exception("Opps"))
            }

            else -> {
                val errorMessage = jsonError?.asJsonObject?.get("error")?.asString
                if (errorMessage != null) {
                    return Source.Error(RuntimeException(errorMessage))
                }
                return Source.Error(RuntimeException("Data extract error, code: $errorCode"))
            }
        }
    }

    override fun handleException(e: Exception): Source.Error {
        return when (e) {
            is MalformedJsonException -> Source.Error(e)
//            is IOException -> Source.Error(RuntimeException(LanguageHelper.resources?.getString(R.string.offline_message)))
            else -> Source.Error(e)
        }
    }

    fun showLogin() {
        cancelChildrenRequests()
    }

    private fun showNewVersionDialog() {

    }
}