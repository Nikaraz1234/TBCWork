package com.example.tbcworks.domain.usecase.firebase

import com.example.tbcworks.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetCurrentUserIdUseCase @Inject constructor(
    private val repo: FirebaseRepository
) {
    operator fun invoke(): String? = repo.getCurrentUserId()
}