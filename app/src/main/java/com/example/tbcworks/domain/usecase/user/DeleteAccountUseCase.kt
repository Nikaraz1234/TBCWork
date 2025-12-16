package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.deleteAccount()
}


