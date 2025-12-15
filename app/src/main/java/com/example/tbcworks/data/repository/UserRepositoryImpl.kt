package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val handleResponse: HandleResponse
) : UserRepository{

    override suspend fun getUserBalance(userId: String): Flow<Resource<Double>> {
        return handleResponse.safeApiCall {
            val userRef = firestore.collection("users").document(userId)
            val snapshot = userRef.get().await()
            snapshot.getDouble("balance") ?: 0.0
        }.map { it.asResource { it } }
    }

    override suspend fun addMoneyToUser(userId: String, amount: Double): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val userRef = firestore.collection("users").document(userId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentBalance = snapshot.getDouble("balance") ?: 0.0
                transaction.set(userRef, mapOf("balance" to (currentBalance + amount)), SetOptions.merge())
            }.await()
        }.map { it.asResource { } }
    }

    override suspend fun withdrawMoneyFromUser(userId: String, amount: Double): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val userRef = firestore.collection("users").document(userId)

            firestore.runTransaction { transaction ->
                val snapshot = transaction.get(userRef)
                val currentBalance = snapshot.getDouble("balance") ?: 0.0
                if (currentBalance < amount) throw IllegalArgumentException("Insufficient balance")
                transaction.set(userRef, mapOf("balance" to (currentBalance - amount)), SetOptions.merge())
            }.await()
        }.map { it.asResource { } }
    }

}
