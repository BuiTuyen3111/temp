package com.zip.lock.screen.wallpapers.data.source.remote.network

import com.zip.lock.screen.wallpapers.utils.AppLog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface APIService {

    fun <T> createAPI(clazz: Class<T>, baseUrl: String, token: () -> String): T {
        return Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                    .addInterceptor { chain ->
                        val request = addHeaderRequest(chain, token.invoke())
                        AppLog.apiInfo(TAG, "Sending request: ${request.url} with headers ${request.headers}")
                        val res = chain.proceed(request)
                        AppLog.apiInfo(TAG, "Received response for ${res.request.url} with status ${res.code}")
                        handlerResponseCode(res)
                        res
                    }
                    .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
                    .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(clazz)
    }

    /**
     * add header request API
     */
    private fun addHeaderRequest(chain: Interceptor.Chain, token: String): Request {
        val request = chain.request().newBuilder()
        return request.build()
    }

    private fun handlerResponseCode(response: Response) {
        when (response.code) {
            in 200..300 -> {}
            else -> {}
        }
        AppLog.apiInfo(TAG, "handlerResponseCode: ${response.code}")
    }

    /**
     * check json response valid
     */
    private fun isJsonValid(jsonString: String): Boolean {
        return try {
            JSONObject(jsonString)
            true
        } catch (e: JSONException) {
            false
        }
    }

    companion object {
        const val TAG = "APIService"
        const val CONNECT_TIME_OUT: Long = 3000
        const val READ_TIME_OUT: Long = 15000
        const val WRITE_TIME_OUT: Long = 15000
        const val TIME_OUT: Long = 25000
    }
}