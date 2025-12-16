package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.extension.toUnitResource
import com.example.tbcworks.data.mapper.pot.toDomain
import com.example.tbcworks.data.mapper.pot.toRequest
import com.example.tbcworks.data.model.pot.PotResponseDto
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.repository.PotRepository
import com.example.tbcworks.domain.usecase.exceptions.InsufficientFundsException
import com.example.tbcworks.domain.usecase.exceptions.InsufficientPotBalanceException
import com.example.tbcworks.domain.usecase.exceptions.PotNotFoundException
import com.example.tbcworks.domain.usecase.exceptions.PotTargetExceededException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PotRepositoryImpl @Inject constructor(
    private val firestoreHelper: FirestoreHelper,
    private val handleResponse: HandleResponse
) : PotRepository {

    override suspend fun addPot(userId: String, pot: Pot): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            val newPotRef = firestoreHelper.potsCollectionRef(userId).document()
            val request = pot.copy(id = newPotRef.id).toRequest()
            newPotRef.set(request).await()
        }.toUnitResource()

    override suspend fun getPots(userId: String): Flow<Resource<List<Pot>>> =
        handleResponse.safeApiCall {
            val snapshot = firestoreHelper.potsCollectionRef(userId).get().await()
            snapshot.toObjects(PotResponseDto::class.java)
        }.map { it.asResource { list -> list.map { dto -> dto.toDomain() } } }

    override suspend fun deletePot(userId: String, potId: String): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
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
        }.toUnitResource()

    override suspend fun editPot(userId: String, pot: Pot): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            firestoreHelper.potRef(userId, pot.id).set(pot.toRequest()).await()
        }.toUnitResource()

    override suspend fun addMoney(
        userId: String,
        potId: String,
        amount: Double
    ): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            val userRef = firestoreHelper.userRef(userId)
            val potRef = firestoreHelper.potRef(userId, potId)

            userRef.firestore.runTransaction { transaction ->

                val userSnapshot = transaction.get(userRef)
                val currentUserBalance =
                    userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

                if (currentUserBalance < amount) {
                    throw InsufficientFundsException()
                }

                val potSnapshot = transaction.get(potRef)

                val currentPotBalance =
                    potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0

                val targetAmount =
                    potSnapshot.getDouble(FirestoreFields.TARGET_AMOUNT) ?: 0.0

                if (currentPotBalance + amount > targetAmount) {
                    throw PotTargetExceededException()
                }

                transaction.update(
                    userRef,
                    FirestoreFields.BALANCE,
                    currentUserBalance - amount
                )

                transaction.update(
                    potRef,
                    FirestoreFields.AMOUNT,
                    currentPotBalance + amount
                )
            }.await()
        }.toUnitResource()


    override suspend fun withdrawMoney(userId: String, potId: String, amount: Double): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
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
        }.toUnitResource()

    override suspend fun getUserPotsCount(userId: String): Flow<Resource<Int>> =
        handleResponse.safeApiCall {
            val snapshot = firestoreHelper.potsCollectionRef(userId)
                .get()
                .await()
            snapshot.size()
        }.map { it.asResource { it } }

}
