package com.example.tbcworks.data.remote.service

import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreCollections
import com.example.tbcworks.data.mapper.transaction.toRequestDto
import com.example.tbcworks.data.remote.model.transaction.TransactionRequestDto
import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.domain.usecase.exceptions.InsufficientFundsException
import com.example.tbcworks.domain.usecase.exceptions.ReceiverNotFoundException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionService @Inject constructor(
    private val firestoreHelper: FirestoreHelper
) {

    suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String?
    ) {
        val senderRef = firestoreHelper.userRef(senderId)
        val senderSnapshot = senderRef.get().await()
        val senderBalance = senderSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
        if (senderBalance < amount) throw InsufficientFundsException()

        val receiverQuery = firestoreHelper.usersCollection()
            .whereEqualTo(FirestoreFields.EMAIL, receiverEmail)
            .get()
            .await()
        if (receiverQuery.isEmpty) throw ReceiverNotFoundException()
        val receiverRef = receiverQuery.documents.first().reference
        val receiverBalance = receiverRef.get().await().getDouble(FirestoreFields.BALANCE) ?: 0.0

        firestoreHelper.runTransaction { txn ->
            txn.update(senderRef, FirestoreFields.BALANCE, senderBalance - amount)
            txn.update(receiverRef, FirestoreFields.BALANCE, receiverBalance + amount)

            val transactionData = Transaction(
                id = "",
                senderId = senderId,
                receiverEmail = receiverEmail,
                purpose = purpose,
                value = amount,
                date = System.currentTimeMillis().toString(),
                imageUrl = imageUrl
            ).toRequestDto()

            txn.set(firestoreHelper.transactionsCollection().document(), transactionData)
        }
    }

    suspend fun getTransactions(userId: String): List<TransactionRequestDto> =
        firestoreHelper.transactionsCollection()
            .whereEqualTo(FirestoreFields.SENDER_ID, userId)
            .get()
            .await()
            .toObjects(TransactionRequestDto::class.java)
}

