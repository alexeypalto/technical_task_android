package com.alexeyp.data.repository

import com.alexeyp.common.result.Result
import com.alexeyp.network.model.User


interface UserRepository {

    suspend fun getUsersNumber(): Int

    suspend fun getUsers(page: Int, perPage: Int): Result<List<User>>

    suspend fun createUser(user: User): Result<User?>

    suspend fun deleteUser(id: Long): Result<Unit?>
}