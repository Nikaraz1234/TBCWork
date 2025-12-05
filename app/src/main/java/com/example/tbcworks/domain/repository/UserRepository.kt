package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsersFromDb(): Flow<List<User>>
    suspend fun fetchUsersFromApi()
}