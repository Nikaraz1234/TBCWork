package com.example.tbcworks.domain.usecase.login

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(): Flow<Resource<Boolean>> {
        return loginRepository.logout()
    }
}