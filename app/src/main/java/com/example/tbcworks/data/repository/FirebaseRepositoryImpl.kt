package com.example.tbcworks.data.repository

import com.example.tbcworks.data.remote.service.FirebaseService
import com.example.tbcworks.domain.repository.FirebaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService
) : FirebaseRepository {

    override fun getCurrentUserId(): String? = firebaseService.getCurrentUserId()

    override fun getCurrentUserEmail(): String? = firebaseService.getCurrentUserEmail()
}
