package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.firestore.FirestoreCollections
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.extension.toUnitResource
import com.example.tbcworks.data.mapper.transaction.toDomain
import com.example.tbcworks.data.mapper.transaction.toRequestDto
import com.example.tbcworks.data.model.transaction.TransactionRequestDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Transaction
import com.example.tbcworks.domain.repository.TransactionRepository
import com.example.tbcworks.domain.usecase.exceptions.InsufficientFundsException
import com.example.tbcworks.domain.usecase.exceptions.ReceiverNotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val firestoreHelper: FirestoreHelper,
    private val handleResponse: HandleResponse
) : TransactionRepository {

    override suspend fun sendTransaction(
        senderId: String,
        receiverEmail: String,
        amount: Double,
        purpose: String,
        imageUrl: String?
    ): Flow<Resource<Unit>> = handleResponse.safeApiCall {

        val senderRef = firestoreHelper.userRef(senderId)
        val senderSnapshot = senderRef.get().await()
        val senderBalance = senderSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
        if (senderBalance < amount) throw InsufficientFundsException()

        val receiverQuery = firestoreHelper.transactionsCollection().firestore
            .collection(FirestoreCollections.USERS)
            .whereEqualTo(FirestoreFields.EMAIL, receiverEmail)
            .get()
            .await()

        if (receiverQuery.isEmpty) throw ReceiverNotFoundException()
        val receiverRef = receiverQuery.documents.first().reference
        val receiverSnapshot = receiverRef.get().await()
        val receiverBalance = receiverSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

        firestoreHelper.transactionsCollection().firestore.runTransaction { transaction ->
            transaction.update(senderRef, FirestoreFields.BALANCE, senderBalance - amount)
            transaction.update(receiverRef, FirestoreFields.BALANCE, receiverBalance + amount)

            val transactionData = Transaction(
                id = "",
                senderId = senderId,
                receiverEmail = receiverEmail,
                purpose = purpose,
                value = amount,
                date = System.currentTimeMillis().toString(),
                imageUrl = imageUrl
            ).toRequestDto()

            transaction.set(firestoreHelper.transactionsCollection().document(), transactionData)
        }.await()

    }.toUnitResource()

    override suspend fun getTransactions(userId: String): Flow<Resource<List<Transaction>>> =
        handleResponse.safeApiCall {
            val snapshot = firestoreHelper.transactionsCollection()
                .whereEqualTo(FirestoreFields.SENDER_ID, userId)
                .get()
                .await()

            snapshot.toObjects(TransactionRequestDto::class.java)
        }.map { it.asResource { list -> list.map { dto -> dto.toDomain() }.sortedBy { it.date } } }
}
