package com.example.composemvvm.usecases

import android.os.Handler
import android.os.Looper
import com.example.composemvvm.core.network.Source
import com.example.composemvvm.network.RemoteDataSource
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

abstract class BaseUseCase {

    protected val remoteDataSource = RemoteDataSource()
}