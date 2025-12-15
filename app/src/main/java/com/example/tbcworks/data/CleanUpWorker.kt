package com.example.tbcworks.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class CleanUpWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            delay(2000)
            throw Exception()
            Result.success()
        } catch (e: Exception) {
            //Result.failure()
            Result.retry()
        }
    }
}
