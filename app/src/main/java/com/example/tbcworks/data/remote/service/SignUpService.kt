package com.example.tbcworks.data.remote.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpService @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun createUser(
        email: String,
        password: String
    ): FirebaseUser {
        val result = auth
            .createUserWithEmailAndPassword(email, password)
            .await()

        return result.user
            ?: throw IllegalStateException("User creation failed")
    }

    suspend fun getIdToken(user: FirebaseUser): String =
        user.getIdToken(true)
            .await()
            .token
            ?: throw IllegalStateException("Token generation failed")
}
