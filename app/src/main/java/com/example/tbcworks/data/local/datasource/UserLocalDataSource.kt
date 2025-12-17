package com.example.tbcworks.data.local.datasource

import com.example.tbcworks.data.local.dao.UserDao
import com.example.tbcworks.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {
    fun getUser(userId: String): Flow<UserEntity?> = userDao.getUserById(userId)
    suspend fun addOrUpdateUser(user: UserEntity) = userDao.addOrUpdateUser(user)
    suspend fun deleteUser(userId: String) = userDao.deleteUser(userId)
    fun getUserByEmail(email: String): Flow<UserEntity?> = userDao.getUserByEmail(email)

}
