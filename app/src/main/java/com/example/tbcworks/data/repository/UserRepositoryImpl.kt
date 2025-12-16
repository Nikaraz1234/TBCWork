package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val handleResponse: HandleResponse,
    private val firebaseAuth: FirebaseAuth
) : UserRepository{

    override fun getUserBalance(userId: String): Flow<Resource<Double>> = flow {
        emit(Resource.Loading)
        val listenerFlow = callbackFlow<Double> {
            val listener = firestore.collection("users").document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener
                    val balance = snapshot?.getDouble("balance") ?: 0.0
                    trySend(balance)
                }
            awaitClose { listener.remove() }
        }

        listenerFlow.collect { balance ->
            emit(Resource.Success(balance))
        }
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

    override suspend fun deleteAccount(): Flow<Resource<Unit>> = handleResponse.safeApiCall {
        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
            ?: throw Exception("No logged in user")
        val userId = currentUser.uid

        firestore.runTransaction { transaction ->
            val userRef = firestore.collection("users").document(userId)
            transaction.delete(userRef)
        }.await()

        currentUser.delete().await()
    }.map { it.asResource { } }


    override suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            val user = firebaseAuth.currentUser
                ?: throw Exception("No logged in user")

            val email = user.email ?: throw Exception("User email not found")

            val credential = com.google.firebase.auth.EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential).await()

            user.updatePassword(newPassword).await()
        }.map { it.asResource { } }





}
