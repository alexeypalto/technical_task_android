package com.alexeyp.network

import com.alexeyp.common.result.Result
import com.alexeyp.network.model.User

interface NetworkDataSource {
    suspend fun getUsersNumber(): Int?
    suspend fun getUsers(page: Int, perPage: Int): Result<List<User>>
    suspend fun create(user: User): Result<User?>
    suspend fun delete(userId: Long): Result<Unit>
}