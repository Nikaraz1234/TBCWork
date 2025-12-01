package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetLoginResponse
import com.example.tbcworks.domain.model.GetUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(email: String, password: String) : Flow<Resource<GetLoginResponse>>
}