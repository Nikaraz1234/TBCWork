package com.example.tbcworks.data.repository

import com.example.tbcworks.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : FirebaseRepository {
    override fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
}