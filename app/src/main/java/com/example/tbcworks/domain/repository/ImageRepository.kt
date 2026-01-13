package com.example.tbcworks.domain.repository

import android.net.Uri

interface ImageRepository {
    suspend fun uploadImage(uri: Uri): Result<Unit>
    suspend fun getAllImages(): List<String>
}