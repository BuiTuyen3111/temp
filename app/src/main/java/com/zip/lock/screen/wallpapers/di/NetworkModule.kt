package com.zip.lock.screen.wallpapers.di

import android.content.Context
import android.provider.Settings
import com.zip.lock.screen.wallpapers.BuildConfig
import com.zip.lock.screen.wallpapers.data.source.remote.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://your.api/" // can inject dynamically if needed
    private const val CONNECT_TIME_OUT: Long = 3000
    private const val READ_TIME_OUT: Long = 15000
    private const val WRITE_TIME_OUT: Long = 15000
    private const val TIME_OUT: Long = 25000

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(WRITE_TIME_OUT, TimeUnit.MILLISECONDS)
            .callTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }

}