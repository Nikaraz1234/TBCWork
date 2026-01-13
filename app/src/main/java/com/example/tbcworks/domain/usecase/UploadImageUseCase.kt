package com.example.tbcworks.domain.usecase

import android.net.Uri
import com.example.tbcworks.domain.repository.ImageRepository
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(uri: Uri): Result<Unit> {
        return repository.uploadImage(uri)
    }
}