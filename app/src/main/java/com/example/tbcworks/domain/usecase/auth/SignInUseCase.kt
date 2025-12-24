package com.example.tbcworks.domain.usecase.auth

import com.example.tbcworks.domain.model.auth.AuthToken
import com.example.tbcworks.domain.model.auth.SignIn
import com.example.tbcworks.domain.repository.SignInRepository
import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: SignInRepository
) {

    suspend operator fun invoke(params: SignIn): Flow<Resource<AuthToken>> {
        return repository.signIn(params)
    }
}