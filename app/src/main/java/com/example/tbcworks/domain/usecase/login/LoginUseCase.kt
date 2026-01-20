package com.example.tbcworks.domain.usecase.login

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        return loginRepository.login(email, password)
    }
}
