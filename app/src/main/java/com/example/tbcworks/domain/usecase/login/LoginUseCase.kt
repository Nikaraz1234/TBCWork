package com.example.tbcworks.domain.usecase.login

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return repository.login(email, password)
    }
}