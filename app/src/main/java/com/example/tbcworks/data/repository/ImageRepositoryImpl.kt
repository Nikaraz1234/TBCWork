package com.example.tbcworks.data.repository

import android.net.Uri
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.tbcworks.domain.StorageKeys
import com.example.tbcworks.domain.repository.ImageRepository
import com.example.tbcworks.worker.UploadWorker
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val workManager: WorkManager,
    private val firebaseStorage: FirebaseStorage
) : ImageRepository {

    override suspend fun uploadImage(uri: Uri): Result<Unit> {
        return try {
            val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>()
                .setInputData(workDataOf(StorageKeys.IMAGE_URI to uri.toString()))
                .build()

            workManager.enqueue(uploadRequest)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllImages(): List<String> {
        return try {
            val storageRef = firebaseStorage.reference.child(StorageKeys.IMAGES_FOLDER)
            val result = storageRef.listAll().await()
            result.items.map { it.downloadUrl.await().toString() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}