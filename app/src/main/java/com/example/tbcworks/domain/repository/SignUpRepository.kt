package com.example.tbcworks.domain.repository


import com.example.tbcworks.domain.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SignUpRepository {
    fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>>
}