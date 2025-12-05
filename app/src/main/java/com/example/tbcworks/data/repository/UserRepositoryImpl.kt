package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.local.dao.UserDao
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.mapper.toEntity
import com.example.tbcworks.data.service.UserService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val api: UserService,
    private val handleResponse: HandleResponse
) : UserRepository {

    override fun getUsersFromDb(): Flow<List<User>> =
        userDao.getAllUsers()
            .map { it.map { entity -> entity.toDomain() } }



    override suspend fun fetchUsersFromApi() {
        handleResponse.safeApiCall { api.getUsers() }
            .collect { resource ->
                resource.asResource { dtoList ->
                    userDao.insert(dtoList.map { it.toEntity() })
                }
            }
    }


}
