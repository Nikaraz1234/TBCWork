package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.HandleResponse
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.User
import com.example.tbcworks.domain.repository.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val handleResponse: HandleResponse
) : RegisterRepository {

    override fun register(email: String, password: String): Flow<Resource<User>> {
        return handleResponse.safeApiCall {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
            firebaseUser?.let {
                User(it.uid, it.email ?: "")
            } ?: throw Exception("Registration failed")
        }
    }
}
