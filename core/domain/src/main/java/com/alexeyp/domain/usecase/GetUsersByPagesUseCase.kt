package com.alexeyp.domain.usecase

import com.alexeyp.data.repository.UserRepository
import com.alexeyp.network.model.User
import javax.inject.Inject

class GetUsersByPagesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(page: Int, perPage: Int): com.alexeyp.common.result.Result<List<User>> {
        return repository.getUsers(page, perPage)
    }
}