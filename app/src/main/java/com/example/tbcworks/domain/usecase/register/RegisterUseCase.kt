package com.example.tbcworks.domain.usecase.register

import com.example.tbcworks.domain.repository.RegisterRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.register(email, password)
}