package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.auth.SignUp
import com.example.tbcworks.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    fun signUp(user: SignUp): Flow<Resource<User>>
}