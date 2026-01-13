package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.repository.ImageRepository
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getAllImages()
    }
}