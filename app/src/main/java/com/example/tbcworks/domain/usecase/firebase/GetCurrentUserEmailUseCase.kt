package com.example.tbcworks.domain.usecase.firebase


import com.example.tbcworks.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetCurrentUserEmailUseCase @Inject constructor(
    private val userRepository: FirebaseRepository
) {
    operator fun invoke(): String? {
        return userRepository.getCurrentUserEmail()
    }
}