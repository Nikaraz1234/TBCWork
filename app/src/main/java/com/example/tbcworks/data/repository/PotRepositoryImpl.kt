package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.pot.toDomain
import com.example.tbcworks.data.mapper.pot.toRequest
import com.example.tbcworks.data.model.pot.PotResponseDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.repository.PotRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PotRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val handleResponse: HandleResponse
) : PotRepository {

    override suspend fun addPot(userId: String, pot: Pot): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val docRef = firestore.collection("users")
                .document(userId)
                .collection("pots")
                .document()

            val request = pot.copy(id = docRef.id).toRequest()
            docRef.set(request).await()
        }.map { it.asResource { } } // make sure asResource<Unit> returns Unit
    }

    override suspend fun getPots(userId: String): Flow<Resource<List<Pot>>> {
        val userCollection = firestore.collection("users")
            .document(userId)
            .collection("pots")

        return handleResponse.safeApiCall {
            val snapshot = userCollection.get().await()
            snapshot.toObjects(PotResponseDto::class.java)
        }.map { it.asResource { list -> list.map { dto -> dto.toDomain() } } }
    }

    override suspend fun deletePot(userId: String, potId: String): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            firestore.collection("users")
                .document(userId)
                .collection("pots")
                .document(potId)
                .delete()
                .await()
        }.map { it.asResource { } }
    }

    override suspend fun editPot(userId: String, pot: Pot): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            firestore.collection("users")
                .document(userId)
                .collection("pots")
                .document(pot.id)
                .set(pot.toRequest())
                .await()
        }.map { it.asResource { } }
    }

    override suspend fun addMoney(userId: String, potId: String, amount: Double): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val userRef = firestore.collection("users").document(userId)
            val potRef = userRef.collection("pots").document(potId)

            firestore.runTransaction { transaction ->
                val userSnapshot = transaction.get(userRef)
                val currentUserBalance = userSnapshot.getDouble("balance") ?: 0.0
                if (currentUserBalance < amount) throw IllegalArgumentException("Insufficient funds")

                val potSnapshot = transaction.get(potRef)
                val currentPotBalance = potSnapshot.getDouble("amount") ?: 0.0

                transaction.update(userRef, "balance", currentUserBalance - amount)
                transaction.update(potRef, "amount", currentPotBalance + amount)
            }.await()
        }.map { it.asResource { } }
    }


    override suspend fun withdrawMoney(userId: String, potId: String, amount: Double): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val userRef = firestore.collection("users").document(userId)
            val potRef = userRef.collection("pots").document(potId)

            firestore.runTransaction { transaction ->
                val potSnapshot = transaction.get(potRef)
                val currentPotBalance = potSnapshot.getDouble("amount") ?: 0.0
                if (currentPotBalance < amount) throw IllegalArgumentException("Insufficient balance in pot")

                val userSnapshot = transaction.get(userRef)
                val currentUserBalance = userSnapshot.getDouble("balance") ?: 0.0

                transaction.update(potRef, "amount", currentPotBalance - amount)
                transaction.update(userRef, "balance", currentUserBalance + amount)
            }.await()
        }.map { it.asResource { } }
    }
}
