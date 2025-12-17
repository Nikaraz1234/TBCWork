package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.firestore.FirestoreFields
import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.common.network.NetworkHelper
import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.extension.toUnitResource
import com.example.tbcworks.data.local.datasource.PotLocalDataSource
import com.example.tbcworks.data.local.datasource.UserLocalDataSource
import com.example.tbcworks.data.local.entity.PotEntity
import com.example.tbcworks.data.mapper.pot.toData
import com.example.tbcworks.data.mapper.pot.toDomain
import com.example.tbcworks.data.mapper.pot.toEntity
import com.example.tbcworks.data.remote.service.PotService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.repository.PotRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PotRepositoryImpl @Inject constructor(
    private val potService: PotService,
    private val localDataSource: PotLocalDataSource,
    private val networkHelper: NetworkHelper,
    private val handleResponse: HandleResponse,
    private val firestoreHelper: FirestoreHelper,
    private val userLocalDataSource: UserLocalDataSource
) : PotRepository {

    override suspend fun addPot(userId: String, pot: Pot): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            localDataSource.addPot(pot.copy(userId = userId).toEntity())

            if (networkHelper.isNetworkAvailable()) {
                potService.addPot(userId, pot)
            }
        }.toUnitResource()

    override suspend fun getPots(userId: String): Flow<Resource<List<Pot>>> =
        handleResponse.safeApiCall {

            val localPots = localDataSource
                .getPots(userId)
                .first()
                .map { it.toDomain() }

            if (localPots.isNotEmpty() || !networkHelper.isNetworkAvailable()) {
                localPots
            } else {
                val remotePots = potService.getPots(userId)
                    .map { it.toDomain() }

                remotePots.forEach { pot ->
                    localDataSource.addPot(pot.copy(userId = userId).toEntity())
                }

                remotePots
            }
        }.map { resource ->
            resource.asResource { pots ->
                pots.sortedBy { it.id }
            }
        }


    override suspend fun deletePot(userId: String, potId: String): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {

            firestoreHelper.runTransaction { transaction ->
                val potDocRef = firestoreHelper.potRef(userId, potId)
                val userDocRef = firestoreHelper.userRef(userId)

                val potSnapshot = transaction.get(potDocRef)
                val userSnapshot = transaction.get(userDocRef)

                val potAmount = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
                val currentBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

                transaction.delete(potDocRef)
                transaction.update(userDocRef, FirestoreFields.BALANCE, currentBalance + potAmount)
            }

            val localPot = localDataSource.getPots(userId).first().find { it.id == potId }
            localPot?.let { localDataSource.deletePot(it) }

            val localUser = userLocalDataSource.getUser(userId).first()
            localUser?.let { userLocalDataSource.addOrUpdateUser(it.copy(balance = it.balance + (localPot?.amount ?: 0.0))) }

        }.toUnitResource()



    override suspend fun editPot(userId: String, pot: Pot): Flow<Resource<Unit>> =
        handleResponse.safeApiCall {
            localDataSource.editPot(pot.copy(userId = userId).toEntity())

            if (networkHelper.isNetworkAvailable()) {
                potService.editPot(userId, pot)
            }
        }.toUnitResource()

    override suspend fun addMoney(
        userId: String,
        potId: String,
        amount: Double
    ): Flow<Resource<Unit>> = handleResponse.safeApiCall {

        val localUser = userLocalDataSource.getUser(userId).first()
        val userBalance = localUser?.balance ?: 0.0

        if (userBalance < amount) {
            throw Exception("Insufficient balance")
        }

        firestoreHelper.runTransaction { transaction ->
            val potDocRef = firestoreHelper.potRef(userId, potId)
            val userDocRef = firestoreHelper.userRef(userId)

            val potSnapshot = transaction.get(potDocRef)
            val userSnapshot = transaction.get(userDocRef)

            val currentPotAmount = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
            val currentUserBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0
            val targetAmount = potSnapshot.getDouble(FirestoreFields.TARGET_AMOUNT) ?: Double.MAX_VALUE

            if (currentUserBalance < amount) {
                throw Exception("Insufficient balance")
            }

            if (currentPotAmount + amount > targetAmount) {
                throw Exception("Deposit exceeds pot target")
            }

            transaction.update(potDocRef, FirestoreFields.AMOUNT, currentPotAmount + amount)
            transaction.update(userDocRef, FirestoreFields.BALANCE, currentUserBalance - amount)
        }

        val localPot = localDataSource.getPots(userId).first().find { it.id == potId }
        localPot?.let { localDataSource.editPot(it.copy(amount = it.amount + amount)) }

        localUser?.let { userLocalDataSource.addOrUpdateUser(it.copy(balance = it.balance - amount)) }

    }.toUnitResource()




    override suspend fun withdrawMoney(
        userId: String,
        potId: String,
        amount: Double
    ): Flow<Resource<Unit>> = handleResponse.safeApiCall {

        firestoreHelper.runTransaction { transaction ->
            val potDocRef = firestoreHelper.potRef(userId, potId)
            val userDocRef = firestoreHelper.userRef(userId)

            val potSnapshot = transaction.get(potDocRef)
            val userSnapshot = transaction.get(userDocRef)

            val currentPotAmount = potSnapshot.getDouble(FirestoreFields.AMOUNT) ?: 0.0
            val currentUserBalance = userSnapshot.getDouble(FirestoreFields.BALANCE) ?: 0.0

            if (currentPotAmount < amount) throw Exception("Insufficient funds in pot")

            transaction.update(potDocRef, FirestoreFields.AMOUNT, currentPotAmount - amount)
            transaction.update(userDocRef, FirestoreFields.BALANCE, currentUserBalance + amount)
        }

        val localPot = localDataSource.getPots(userId).first().find { it.id == potId }
        localPot?.let { localDataSource.editPot(it.copy(amount = it.amount - amount)) }

        val localUser = userLocalDataSource.getUser(userId).first()
        localUser?.let { userLocalDataSource.addOrUpdateUser(it.copy(balance = it.balance + amount)) }

    }.toUnitResource()


    override suspend fun getUserPotsCount(userId: String): Flow<Resource<Int>> =
        handleResponse.safeApiCall {
            localDataSource.getPotsCount(userId).first()
        }.map { it.asResource { it } }

    suspend fun syncPot(pot: PotEntity) {
        firestoreHelper.runTransaction { transaction ->
            val potRef = firestoreHelper.potRef(pot.userId, pot.id)
            transaction.set(potRef, pot.toData())
        }
        localDataSource.markAsSynced(pot)
    }

}
