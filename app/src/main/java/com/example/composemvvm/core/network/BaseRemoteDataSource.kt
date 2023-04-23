package com.example.composemvvm.core.network

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.JsonElement
import kotlinx.coroutines.Job
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.Executors

abstract class BaseRemoteDataSource {

    private val gson = Gson()
    private val uiHandler = Handler(Looper.getMainLooper())

    companion object {
        private var job = Job()
        private val threadDispatcher = Executors.newFixedThreadPool(3).asCoroutineDispatcher()
    }

    suspend fun <S : Any> executeNetworkRequest(call: suspend () -> Response<S>): Source<S> {
        return withContext(threadDispatcher + job) {
            runBlocking {
                try {
                    val response = call()
                    if (response.isSuccessful && response.body() != null) {
                        Source.Success(response.body())
                    } else {
                        val jsonError = extractError(response.errorBody())
                        handleServerError(jsonError, response.code())
                    }
                } catch (e: Exception) {
                    handleException(e)
                }
            }
        }
    }

    protected abstract fun handleServerError(jsonError: JsonElement?, errorCode: Int): Source.Error
    protected abstract fun handleException(e: Exception): Source.Error

    protected fun cancelChildrenRequests() {
        job.cancelChildren()
    }

    private fun extractError(errorBody: ResponseBody?): JsonElement? {
        errorBody ?: return null
        return try {
            gson.fromJson(errorBody.charStream(), JsonElement::class.java)
        } catch (e: Exception) {
            null
        }
    }

    protected fun runInUi(call: () -> Unit) {
        uiHandler.post { call.invoke() }
    }
}