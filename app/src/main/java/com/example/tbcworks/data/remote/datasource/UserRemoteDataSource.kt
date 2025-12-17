package com.example.tbcworks.data.remote.datasource

import com.example.tbcworks.data.common.firestore.AuthHelper
import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val firestoreHelper: FirestoreHelper,
    private val authHelper: AuthHelper,
    private val firebaseAuth: FirebaseAuth
) {

    fun getCurrentUserId(): String = authHelper.currentUserId()

    suspend fun addMoney(userId: String, amount: Double) {
        firestoreHelper.runTransaction { transaction ->
            val userRef = firestoreHelper.userRef(userId)
            val snapshot = transaction.get(userRef)
            val currentBalance = snapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
            transaction.update(userRef, FirestoreFields.BALANCE, currentBalance + amount)
        }
    }

    suspend fun withdrawMoney(userId: String, amount: Double) {
        firestoreHelper.runTransaction { transaction ->
            val userRef = firestoreHelper.userRef(userId)
            val snapshot = transaction.get(userRef)
            val currentBalance = snapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
            transaction.update(userRef, FirestoreFields.BALANCE, currentBalance - amount)
        }
    }

    suspend fun deleteUser(userId: String, currentPassword: String) {
        val user = firebaseAuth.currentUser
        val credential = com.google.firebase.auth.EmailAuthProvider
            .getCredential(user?.email ?: "", currentPassword)
        user?.reauthenticate(credential)?.await()

        firestoreHelper.userRef(userId).delete().await()
        user?.delete()?.await()
    }

    suspend fun changePassword(userId: String, currentPassword: String, newPassword: String) {
        val user = firebaseAuth.currentUser
        val credential = com.google.firebase.auth.EmailAuthProvider
            .getCredential(user?.email ?: "", currentPassword)
        user?.reauthenticate(credential)?.await()

        user?.updatePassword(newPassword)?.await()
    }
}
