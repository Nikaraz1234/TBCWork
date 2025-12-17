package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(currentPassword: String): Flow<Resource<Unit>> {
        return repository.deleteAccount(currentPassword)
    }
}