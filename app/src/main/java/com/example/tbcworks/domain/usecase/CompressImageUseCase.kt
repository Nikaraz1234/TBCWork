package com.example.tbcworks.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class CompressImageUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun execute(uri: Uri, quality: Int = 80): Uri {
        val inputStream = context.contentResolver.openInputStream(uri)
            ?: throw IllegalArgumentException("Cannot open input stream for $uri")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val compressedFile = File(context.cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        FileOutputStream(compressedFile).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
        }

        return compressedFile.toUri()
    }
}