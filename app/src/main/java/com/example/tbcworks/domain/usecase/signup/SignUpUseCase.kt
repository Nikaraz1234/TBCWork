package com.example.tbcworks.domain.usecase.signup

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: SignUpRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return repository.signUp(email, password)
    }
}