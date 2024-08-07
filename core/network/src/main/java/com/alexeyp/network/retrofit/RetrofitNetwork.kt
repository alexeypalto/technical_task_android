package com.alexeyp.network.retrofit

import com.alexeyp.common.result.HEADER_LIMIT
import com.alexeyp.common.result.HEADER_PAGES
import com.alexeyp.common.result.Result
import com.alexeyp.network.NetworkDataSource
import com.alexeyp.network.model.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitNetworkApi {
    @GET("/public/v2/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/public/v2/users")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<User>>

    @POST("/public/v2/users")
    suspend fun create(@Body user: User): Response<User>

    @DELETE("/public/v2/users/{userId}")
    suspend fun delete(@Path("userId") userId: Long): Response<Unit>
}

private const val BASE_URL = "https://gorest.co.in/"
private const val TOKEN = "Bearer c54b2da98bf776667fbafe72402e9fd002dd9f3de6e823887db739f518b7a8eb"

@Singleton
internal class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getUsersNumber(): Int? {
        val response = networkApi.getUsers()
        try {
            val headers = response.headers()
            val totalPages = headers[HEADER_PAGES]?.toInt() ?: -1
            val defaultLimit = headers[HEADER_LIMIT]?.toInt() ?: -1
            return totalPages * defaultLimit
        } catch (e: Exception) {
            return null
        }
    }


    override suspend fun getUsers(page: Int, perPage: Int): Result<List<User>> {
        val response = networkApi.getUsers(page, perPage)
        return try {
            Result.Success.Value(response.body() ?: emptyList())
        } catch (e: HttpException) {
            Result.Failure.Error(com.alexeyp.common.result.HttpException(response.code()))
        } catch (e: Exception) {
            Result.Failure.Error(Throwable(response.message()))
        }
    }

    override suspend fun create(user: User): Result<User?> {
        val response = networkApi.create(user)
        return try {
            Result.Success.Value(response.body())
        } catch (e: HttpException) {
            Result.Failure.Error(com.alexeyp.common.result.HttpException(response.code()))
        } catch (e: Exception) {
            Result.Failure.Error(Throwable(response.message()))
        }
    }

    override suspend fun delete(userId: Long): Result<Unit> {
        val response = networkApi.delete(userId)
        return try {
            Result.Success.Value(response.body() ?: Unit)
        } catch (e: HttpException) {
            Result.Failure.Error(com.alexeyp.common.result.HttpException(response.code()))
        } catch (e: Exception) {
            Result.Failure.Error(Throwable(response.message()))
        }
    }
}

fun getAuthInterceptor(): Interceptor {
    return Interceptor { chain ->
        val builder = chain.request().newBuilder()
        TOKEN.let { token ->
            builder.addHeader("Authorization", token)
        }
        val request = builder.build()
        chain.proceed(request)
    }
}