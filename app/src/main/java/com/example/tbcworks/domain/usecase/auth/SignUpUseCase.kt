package com.example.tbcworks.domain.usecase.auth

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.domain.model.user.User
import com.example.tbcworks.domain.repository.SignUpRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SignUpUseCase @Inject constructor(private val repository: SignUpRepository) {
    operator fun invoke(user: SignUp): Flow<Resource<User>> {
        return repository.signUp(user)
    }
}