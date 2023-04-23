package com.example.composemvvm.core.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit

abstract class BaseNetworkAPIConfig<SERVICE> {

    private val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz"

    private val dateTypeAdapter: JsonDeserializer<Date> by lazy {
        val dateGsonAdapter = GsonBuilder()
            .setDateFormat(dateFormat)
            .create()
            .getAdapter(Date::class.java)
            .nullSafe()
        return@lazy JsonDeserializer<Date> { json, _, _ ->
            try {
                dateGsonAdapter.fromJsonTree(json)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun createNetworkService(): SERVICE {
        val gson = GsonBuilder()
            .registerTypeAdapter(Date::class.java, dateTypeAdapter)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(createConfiguredHttpClientBuilder().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(getServiceClass())
    }

    private fun createConfiguredHttpClientBuilder(): OkHttpClient.Builder {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

//        if (isSslDisable()) {
//            disableSslVerification(okHttpClient)
//        }

        applyInterceptor()?.forEach { interceptor ->
            okHttpClient.addInterceptor(interceptor)
        }

        if (isLoggingEnable()) {
            okHttpClient.addInterceptor(createLoggingInterceptor())
        }

        return okHttpClient
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = useLoggingLevel()
        return loggingInterceptor
    }

//    private fun disableSslVerification(okHttpBuilder: OkHttpClient.Builder): OkHttpClient.Builder {
//        val x509TrustManager: X509TrustManager = object : X509TrustManager {
//            override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
//            override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {}
//            override fun getAcceptedIssuers(): Array<X509Certificate> {
//                return arrayOf()
//            }
//        }
//        val trustAllCerts = arrayOf<TrustManager>(x509TrustManager)
//        val sslContext = SSLContext.getInstance("TLSv1.3")
//        sslContext.init(null, trustAllCerts, SecureRandom())
//        okHttpBuilder.sslSocketFactory(sslContext.socketFactory, x509TrustManager)
//        okHttpBuilder.hostnameVerifier { _, _ -> true }
//        return okHttpBuilder
//    }

    private fun useLoggingLevel(): HttpLoggingInterceptor.Level {
        return HttpLoggingInterceptor.Level.BODY
    }

    protected abstract fun isSslDisable(): Boolean

    protected abstract fun getBaseUrl(): String

    protected abstract fun isLoggingEnable(): Boolean

    protected abstract fun applyInterceptor(): List<Interceptor>?

    protected abstract fun getServiceClass(): Class<SERVICE>
}