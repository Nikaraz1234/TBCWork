package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.datastore.DataStoreManager
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.SignUpRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val handleResponse: HandleResponse,
    private val dataStore: DataStoreManager,
    private val firestore: FirebaseFirestore
) : SignUpRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun signUp(email: String, password: String): Flow<Resource<FirebaseUser>> =
        handleResponse.safeApiCall {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user ?: throw Exception("User is null")

            val tokenResult = user.getIdToken(true).await()
            val token = tokenResult.token ?: throw Exception("Token is null")

            dataStore.save("user_token", token)

            val userMap = hashMapOf(
                "uid" to user.uid,
                "email" to email,
                "name" to "",
                "balance" to 0
            )
            firestore.collection("users").document(user.uid)
                .set(userMap).await()

            user
        }
}