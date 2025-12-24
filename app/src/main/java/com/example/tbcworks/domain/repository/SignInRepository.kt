package com.example.tbcworks.domain.repository

import com.example.tbcworks.data.model.auth.sign_in.SignInResponseDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.auth.AuthToken
import com.example.tbcworks.domain.model.auth.SignIn
import kotlinx.coroutines.flow.Flow

interface SignInRepository {
    fun signIn(params: SignIn): Flow<Resource<AuthToken>>
}