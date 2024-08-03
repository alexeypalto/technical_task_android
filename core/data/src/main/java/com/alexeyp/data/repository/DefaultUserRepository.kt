package com.alexeyp.data.repository

import com.alexeyp.network.NetworkDataSource
import com.alexeyp.common.result.Result
import com.alexeyp.network.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultUserRepository @Inject constructor(private val dataSource: NetworkDataSource) :
    UserRepository {

    override suspend fun getUsersNumber(): Int {
        return dataSource.getUsersNumber() ?: 1
    }

    override suspend fun getUsers(page: Int, perPage: Int): Result<List<User>> {
        return dataSource.getUsers(page, perPage)
    }

    override suspend fun createUser(user: User): Result<User?> {
        return dataSource.create(user)
    }

    override suspend fun deleteUser(id: Long): Result<Unit?> {
        return dataSource.delete(id)
    }
}