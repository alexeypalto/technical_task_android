package com.alexeyp.domain.usecase

import com.alexeyp.data.repository.UserRepository
import javax.inject.Inject

class GetUsersNumberUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getUsersNumber()
    }
}