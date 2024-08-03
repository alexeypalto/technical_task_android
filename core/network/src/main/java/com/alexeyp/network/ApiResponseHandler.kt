package com.alexeyp.network

import com.alexeyp.network.retrofit.ApiError
import retrofit2.Response

class ApiResponseHandler {
    companion object {
        fun <T : Any> handle(response: Response<T>): T {
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    return body
                } ?: throw ApiError.EmptyResponse
            } else {
                throw when (response.code()) {
                    401, 403 -> ApiError.Unauthorized
                    429, 503 -> ApiError.ServerUnavailable(response.code())
                    in 500..599 -> ApiError.ServerNotResponding(response.code())
                    else -> ApiError.HttpOther(response.code())
                }
            }
        }
    }
}