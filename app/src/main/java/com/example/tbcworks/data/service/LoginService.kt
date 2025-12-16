package com.example.tbcworks.data.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginService @Inject constructor(
    private val auth: FirebaseAuth
){

    suspend fun signIn(email: String, password: String): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        return result.user ?: throw Exception("User is null")
    }

    suspend fun getIdToken(user: FirebaseUser): String {
        val tokenResult = user.getIdToken(true).await()
        return tokenResult.token ?: throw Exception("Token is null")
    }

    fun signOut() {
        auth.signOut()
    }
}