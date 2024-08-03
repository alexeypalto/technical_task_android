package com.alexeyp.domain.usecase

import com.alexeyp.common.result.Result
import com.alexeyp.data.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Long): Result<Unit?> {
        return repository.deleteUser(id)
    }
}