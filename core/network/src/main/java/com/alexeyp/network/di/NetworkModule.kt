package com.alexeyp.network.di

import com.alexeyp.network.retrofit.getAuthInterceptor
import com.alexeyp.network.utils.CustomHttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addNetworkInterceptor(CustomHttpLoggingInterceptor())
        .addNetworkInterceptor(getAuthInterceptor())
        .build()

}