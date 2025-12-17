package com.example.tbcworks.data.remote.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUser(): FirebaseUser? = firebaseAuth.currentUser
    fun getCurrentUserId(): String? = firebaseAuth.currentUser?.uid
    fun getCurrentUserEmail(): String? = firebaseAuth.currentUser?.email
}
