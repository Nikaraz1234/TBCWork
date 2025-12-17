package com.example.tbcworks.data.common.firestore

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHelper @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {

    fun currentUser() =
        firebaseAuth.currentUser ?: throw Exception("No logged in user")

    fun currentUserId(): String =
        currentUser().uid

    fun currentUserEmail(): String =
        currentUser().email ?: throw Exception("User email not found")
}