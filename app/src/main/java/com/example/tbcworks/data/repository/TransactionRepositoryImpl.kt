package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.network.NetworkHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.toUnitResource
import com.example.tbcworks.data.local.datasource.TransactionLocalDataSource
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import com.example.tbcworks.data.local.entity.TransactionEntity
import com.example.tbcworks.data.mapper.transaction.toDomain
import com.example.tbcworks.data.mapper.transaction.toEntity
import com.example.tbcworks.data.remote.service.TransactionService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val local: TransactionLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val transactionService: TransactionService,
    private val networkHelper: NetworkHelper,
    private val handleResponse: HandleResponse
) : TransactionRepository {

    override suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String?
    ): Flow<Resource<Unit>> = handleResponse.safeApiCall {
        if (!networkHelper.isNetworkAvailable()) {
            throw Exception("Transaction needs internet connection")
        }

        val sender = userLocalDataSource.getUser(senderId).first()
            ?: throw Exception("Sender not found")
        val receiver = userLocalDataSource.getUserByEmail(receiverEmail).first()
            ?: throw Exception("Receiver not found")

        if (sender.balance < amount) throw Exception("Insufficient balance")

        val updatedSender = sender.copy(balance = sender.balance - amount)
        val updatedReceiver = receiver.copy(balance = receiver.balance + amount)
        userLocalDataSource.addOrUpdateUser(updatedSender)
        userLocalDataSource.addOrUpdateUser(updatedReceiver)

        val transactionId = UUID.randomUUID().toString()
        val transactionEntity = TransactionEntity(
            id = transactionId,
            senderId = senderId,
            receiverEmail = receiverEmail,
            value = amount,
            purpose = purpose,
            date = System.currentTimeMillis().toString(),
            imageUrl = imageUrl,
            synced = false
        )
        local.addTransaction(transactionEntity)

        try {
            transactionService.sendTransaction(senderId, receiverEmail, amount, purpose, imageUrl)
            local.markAsSynced(transactionEntity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.toUnitResource()

    override suspend fun getTransactions(userId: String): Flow<Resource<List<Transaction>>> =
        local.observeTransactions(userId)
            .map { entities -> Resource.Success(entities.map { it.toDomain() }) }

    override suspend fun refreshTransactions(userId: String) {
        val remoteTransactions = transactionService.getTransactions(userId)
        local.insertAll(remoteTransactions.map { it.toEntity() })
    }

    suspend fun syncTransaction(transaction: TransactionEntity) {
        try {
            transactionService.sendTransaction(
                transaction.senderId,
                transaction.receiverEmail,
                transaction.value,
                transaction.purpose,
                transaction.imageUrl
            )
            local.markAsSynced(transaction)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

