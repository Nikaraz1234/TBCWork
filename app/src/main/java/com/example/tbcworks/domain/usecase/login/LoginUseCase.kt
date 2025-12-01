package com.example.tbcworks.domain.usecase.login

import com.example.tbcworks.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}