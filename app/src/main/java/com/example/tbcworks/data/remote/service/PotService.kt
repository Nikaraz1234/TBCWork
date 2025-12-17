package com.example.tbcworks.data.remote.service

import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.mapper.pot.toRequest
import com.example.tbcworks.data.remote.model.pot.PotResponseDto
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.usecase.exceptions.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PotService @Inject constructor(
    private val firestoreHelper: FirestoreHelper
) {

    suspend fun addPot(userId: String, pot: Pot) {
        firestoreHelper
            .potsCollectionRef(userId)
            .document(pot.id)
            .set(pot.toRequest())
            .await()
    }

    suspend fun getPots(userId: String) =
        firestoreHelper.potsCollectionRef(userId).get().await().toObjects(PotResponseDto::class.java)

    suspend fun deletePot(userId: String, potId: String) {
        val userRef = firestoreHelper.userRef(userId)
        val potRef = firestoreHelper.potRef(userId, potId)

        userRef.firestore.runTransaction { transaction ->
            val potSnapshot = transaction.get(potRef)
            if (!potSnapshot.exists()) throw PotNotFoundException()

            val potAmount = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
            if (potAmount > 0) {
                val userSnapshot = transaction.get(userRef)
                val currentUserBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
                transaction.update(userRef, FirestoreFields.BALANCE, currentUserBalance + potAmount)
            }

            transaction.delete(potRef)
        }.await()
    }

    suspend fun editPot(userId: String, pot: Pot) {
        firestoreHelper.potRef(userId, pot.id).set(pot.toRequest()).await()
    }

    suspend fun addMoneyToPot(userId: String, potId: String, amount: Double) {
        val userRef = firestoreHelper.userRef(userId)
        val potRef = firestoreHelper.potRef(userId, potId)

        userRef.firestore.runTransaction { transaction ->
            val userSnapshot = transaction.get(userRef)
            val currentUserBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
            if (currentUserBalance < amount) throw InsufficientFundsException()

            val potSnapshot = transaction.get(potRef)
            val currentPotBalance = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
            val targetAmount = potSnapshot.getDouble(FirestoreFields.TARGET_AMOUNT) ?: 0.0

            if (currentPotBalance + amount > targetAmount) throw PotTargetExceededException()

            transaction.update(userRef, FirestoreFields.BALANCE, currentUserBalance - amount)
            transaction.update(potRef, FirestoreFields.AMOUNT, currentPotBalance + amount)
        }.await()
    }

    suspend fun withdrawMoneyFromPot(userId: String, potId: String, amount: Double) {
        val userRef = firestoreHelper.userRef(userId)
        val potRef = firestoreHelper.potRef(userId, potId)

        userRef.firestore.runTransaction { transaction ->
            val potSnapshot = transaction.get(potRef)
            val currentPotBalance = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
            if (currentPotBalance < amount) throw InsufficientPotBalanceException()

            val userSnapshot = transaction.get(userRef)
            val currentUserBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

            transaction.update(potRef, FirestoreFields.AMOUNT, currentPotBalance - amount)
            transaction.update(userRef, FirestoreFields.BALANCE, currentUserBalance + amount)
        }.await()
    }

    suspend fun getUserPotsCount(userId: String): Int =
        firestoreHelper.potsCollectionRef(userId).get().await().size()
}
