package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.model.GetUserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun saveUser(first: String, last: String, email: String)
    fun readUser(): Flow<GetUserData>
}