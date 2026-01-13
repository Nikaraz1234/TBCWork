package com.example.tbcworks.worker

import android.content.Context
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tbcworks.domain.StorageKeys
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UploadWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val uriString = inputData.getString(StorageKeys.IMAGE_URI) ?: return Result.failure()
        val uri = uriString.toUri()

        return try {
            val storageRef = FirebaseStorage.getInstance().reference
            val fileName = "${StorageKeys.IMAGES_FOLDER}/${UUID.randomUUID()}.jpg"
            val imageRef = storageRef.child(fileName)
            imageRef.putFile(uri).await()

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
