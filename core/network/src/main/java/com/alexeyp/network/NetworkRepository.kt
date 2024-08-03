package com.alexeyp.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class NetworkRepository {

    suspend fun <T : Any> getResult(
        request: suspend CoroutineScope.() -> Response<T>
    ): Result<T> {
        return Result.runCatching {
            ApiResponseHandler.handle(
                withContext(Dispatchers.IO) {
                    request.invoke(this)
                }
            )
        }
    }

    suspend fun <T : Any, P : Any> getResult(
        request: suspend CoroutineScope.() -> Response<T>,
        bodyHandler: (T) -> P
    ): Result<P> {
        return Result.runCatching {
            with(ApiResponseHandler.handle(
                withContext(Dispatchers.IO) {
                    request.invoke(this)
                }
            )) {
                bodyHandler.invoke(this)
            }
        }
    }
}