package com.example.tbcworks.data.repository

import com.example.tbcworks.data.mapper.transaction.toRequestDto
import com.example.tbcworks.data.model.transaction.TransactionRequestDto
import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.domain.repository.TransactionRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : TransactionRepository {

    override suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String?
    ) {
        val senderRef = db.collection("users").document(senderId)
        val senderSnapshot = senderRef.get().await()
        val senderBalance = senderSnapshot.getDouble("balance") ?: 0.0

        if (senderBalance < amount) throw Exception("Insufficient balance")

        // Find receiver by email
        val receiverQuery = db.collection("users").whereEqualTo("email", receiverEmail).get().await()
        if (receiverQuery.isEmpty) throw Exception("Receiver not found")
        val receiverRef = receiverQuery.documents.first().reference
        val receiverSnapshot = receiverRef.get().await()
        val receiverBalance = receiverSnapshot.getDouble("balance") ?: 0.0

        // Run atomic transaction
        db.runTransaction { transaction ->
            transaction.update(senderRef, "balance", senderBalance - amount)
            transaction.update(receiverRef, "balance", receiverBalance + amount)

            val transactionData = Transaction(
                id = "", // Firestore generates ID
                senderId = senderId,
                receiverEmail = receiverEmail,
                purpose = purpose,
                value = amount,
                date = System.currentTimeMillis().toString(),
                imageUrl = imageUrl
            ).toRequestDto()

            transaction.set(db.collection("transactions").document(), transactionData)
        }.await()
    }


}
