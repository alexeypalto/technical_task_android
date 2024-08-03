package com.alexeyp.domain.usecase

import com.alexeyp.common.result.Result
import com.alexeyp.data.repository.UserRepository
import com.alexeyp.network.model.User
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User?> {
        return repository.createUser(user)
    }
}