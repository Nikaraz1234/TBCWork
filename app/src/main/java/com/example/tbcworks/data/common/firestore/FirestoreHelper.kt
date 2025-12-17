package com.example.tbcworks.data.common.firestore

import com.google.firebase.firestore.FirebaseFirestore
import com.example.tbcworks.data.common.firestore.FirestoreCollections
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreHelper @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun usersCollection() =
        firestore.collection(FirestoreCollections.USERS)
    fun userRef(userId: String) = firestore.collection(FirestoreCollections.USERS).document(userId)
    fun potRef(userId: String, potId: String) = userRef(userId).collection(FirestoreCollections.POTS).document(potId)
    fun potsCollectionRef(userId: String) = userRef(userId).collection(FirestoreCollections.POTS)
    fun transactionsCollection(): CollectionReference =
        firestore.collection(FirestoreCollections.TRANSACTIONS)

    fun transactionRef(transactionId: String): DocumentReference =
        transactionsCollection().document(transactionId)

    suspend fun runTransaction(
        block: (com.google.firebase.firestore.Transaction) -> Unit
    ) {
        firestore.runTransaction { transaction ->
            block(transaction)
        }.await()
    }
}