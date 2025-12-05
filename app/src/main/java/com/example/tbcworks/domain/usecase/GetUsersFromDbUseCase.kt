package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersFromDbUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.getUsersFromDb()
}