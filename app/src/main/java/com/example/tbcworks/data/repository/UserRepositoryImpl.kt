package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.network.NetworkHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.toUnitResource
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import com.example.tbcworks.data.local.entity.UserEntity
import com.example.tbcworks.data.remote.datasource.UserRemoteDataSource
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    private val networkHelper: NetworkHelper,
    private val handleResponse: HandleResponse
) : UserRepository {

    override fun getUserBalance(userId: String): Flow<Resource<Double>> =
        localDataSource.getUser(userId)
            .map { it?.balance ?: 0.0 }
            .map { Resource.Success(it) as Resource<Double> }

    override suspend fun addMoneyToUser(userId: String, amount: Double) =
        handleResponse.safeApiCall {
            val localUser = localDataSource.getUser(userId).first()
            localUser?.let {
                localDataSource.addOrUpdateUser(it.copy(balance = it.balance + amount))
            }

            if (networkHelper.isNetworkAvailable()) {
                remoteDataSource.addMoney(userId, amount)
            }
        }.toUnitResource()

    override suspend fun withdrawMoneyFromUser(userId: String, amount: Double) =
        handleResponse.safeApiCall {
            val localUser = localDataSource.getUser(userId).first()
            localUser?.let {
                localDataSource.addOrUpdateUser(it.copy(balance = it.balance - amount))
            }

            if (networkHelper.isNetworkAvailable()) {
                remoteDataSource.withdrawMoney(userId, amount)
            }
        }.toUnitResource()


    override suspend fun deleteAccount(currentPassword: String) =
        handleResponse.safeApiCall {
            val userId = remoteDataSource.getCurrentUserId()
            localDataSource.deleteUser(userId)

            if (networkHelper.isNetworkAvailable()) {
                remoteDataSource.deleteUser(userId, currentPassword)
            }
        }.toUnitResource()

    override suspend fun changePassword(currentPassword: String, newPassword: String) =
        handleResponse.safeApiCall {
            if (networkHelper.isNetworkAvailable()) {
                val userId = remoteDataSource.getCurrentUserId()
                remoteDataSource.changePassword(userId, currentPassword, newPassword)
            }
        }.toUnitResource()
}
