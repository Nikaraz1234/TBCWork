package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.domain.repository.UserRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(currentPassword: String, newPassword: String) =
        userRepository.changePassword(currentPassword, newPassword)
}