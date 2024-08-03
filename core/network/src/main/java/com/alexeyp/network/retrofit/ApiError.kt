package com.alexeyp.network.retrofit

sealed class ApiError : Throwable() {
    class ServerUnavailable(val httpCode: Int) : ApiError()
    class ServerNotResponding(val httpCode: Int) : ApiError()
    class HttpOther(val httpCode: Int) : ApiError()
    object Unauthorized : ApiError()
    object EmptyResponse : ApiError()
    class Other : ApiError()
}

