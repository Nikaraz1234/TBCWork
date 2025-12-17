package com.example.tbcworks.data.remote.service

import com.example.tbcworks.data.common.firestore.AuthHelper
import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.domain.usecase.exceptions.InsufficientFundsException
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(
    private val firestoreHelper: FirestoreHelper,
    private val authHelper: AuthHelper
) {
    fun getUserBalance(userId: String): Flow<Double> = callbackFlow {
        val listener = firestoreHelper.userRef(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                val balance = snapshot?.getDouble(FirestoreFields.BALANCE) ?: 0.0
                trySend(balance)
            }

        awaitClose { listener.remove() }
    }

    suspend fun addMoney(userId: String, amount: Double) {
        val userRef = firestoreHelper.userRef(userId)

        firestoreHelper.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentBalance = snapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
            transaction.update(userRef, FirestoreFields.BALANCE, currentBalance + amount)
        }
    }

    suspend fun withdrawMoney(userId: String, amount: Double) {
        val userRef = firestoreHelper.userRef(userId)

        firestoreHelper.runTransaction { transaction ->
            val snapshot = transaction.get(userRef)
            val currentBalance = snapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

            if (currentBalance < amount) throw InsufficientFundsException()

            transaction.update(userRef, FirestoreFields.BALANCE, currentBalance - amount)
        }
    }

    suspend fun deleteAccount(currentPassword: String) {
        val user = authHelper.currentUser()
        val userId = authHelper.currentUserId()

        val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
        user.reauthenticate(credential).await()

        firestoreHelper.userRef(userId).delete().await()

        user.delete().await()
    }

    suspend fun changePassword(currentPassword: String, newPassword: String) {
        val user = authHelper.currentUser()
        val email = authHelper.currentUserEmail()
        val credential = EmailAuthProvider.getCredential(email, currentPassword)
        user.reauthenticate(credential).await()
        user.updatePassword(newPassword).await()
    }
}
