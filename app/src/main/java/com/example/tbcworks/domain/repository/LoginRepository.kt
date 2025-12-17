package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(email: String, password: String): Flow<Resource<FirebaseUser>>
    suspend fun logout()
}