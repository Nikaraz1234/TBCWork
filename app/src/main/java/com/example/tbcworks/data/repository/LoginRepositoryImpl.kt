package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.HandleResponse
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleResponse: HandleResponse
) : LoginRepository {
    override fun login(email: String, password: String): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            firebaseUser?.let {
                User(it.uid, it.email ?: "")
            } ?: throw Exception("Login failed")
        }
    }

    override fun logout(): Flow<Resource<Boolean>> {
        return handleResponse.safeApiCall {
            firebaseAuth.signOut()
            true
        }
    }
}