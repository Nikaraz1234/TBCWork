package com.example.tbcworks.domain.usecase.proto

import com.example.tbcworks.domain.repository.UserDataRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserDataRepository
) {
    suspend operator fun invoke(firstName: String, lastName: String, email: String) {
        repository.saveUser(firstName, lastName, email)
    }
}