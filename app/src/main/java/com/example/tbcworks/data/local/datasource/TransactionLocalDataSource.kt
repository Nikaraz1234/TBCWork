package com.example.tbcworks.data.local.datasource

import com.example.tbcworks.data.local.dao.TransactionDao
import com.example.tbcworks.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionLocalDataSource @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun observeTransactions(userId: String): Flow<List<TransactionEntity>> =
        transactionDao.getTransactions(userId)

    suspend fun addTransaction(transaction: TransactionEntity) =
        transactionDao.addTransaction(transaction)

    suspend fun insertAll(transactions: List<TransactionEntity>) =
        transactionDao.insertAll(transactions)

    fun getUnsyncedTransactions(): Flow<List<TransactionEntity>> = transactionDao.getUnsyncedTransactions()
    suspend fun markAsSynced(transaction: TransactionEntity) = transactionDao.editTransaction(transaction.copy(synced = true))
}