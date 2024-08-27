package com.alexeyp.network.retrofit

sealed class ApiError : Throwable() {
    class ServerUnavailable(val httpCode: Int) : ApiError()
    class ServerNotResponding(val httpCode: Int) : ApiError()
    class HttpOther(val httpCode: Int) : ApiError()
    data object Unauthorized : ApiError() {
        private fun readResolve(): Any = Unauthorized
    }

    data object EmptyResponse : ApiError() {
        private fun readResolve(): Any = EmptyResponse
    }

    class Other : ApiError()
}

