package com.example.tbcworks.data.common.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tbcworks.data.local.datasource.PotLocalDataSource
import com.example.tbcworks.data.local.datasource.TransactionLocalDataSource
import com.example.tbcworks.data.repository.PotRepositoryImpl
import com.example.tbcworks.data.repository.TransactionRepositoryImpl
import kotlinx.coroutines.flow.first

class SyncWorker(
    context: Context,
    params: WorkerParameters,
    private val potLocalDataSource: PotLocalDataSource,
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val potRepository: PotRepositoryImpl,
    private val transactionRepository: TransactionRepositoryImpl
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = try {
        potLocalDataSource.getUnsyncedPots().first().forEach { pot ->
            potRepository.syncPot(pot)
        }

        transactionLocalDataSource.getUnsyncedTransactions().first().forEach { transaction ->
            transactionRepository.syncTransaction(transaction)
        }

        Result.success()
    } catch (e: Exception) {
        e.printStackTrace()
        Result.retry()
    }
}
