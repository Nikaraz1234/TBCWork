package com.example.tbcworks.domain.usecase.login


import com.example.tbcworks.domain.repository.LoginRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke() {
        loginRepository.logout()
    }
}