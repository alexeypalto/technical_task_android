package com.alexeyp.common.result

interface HttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
