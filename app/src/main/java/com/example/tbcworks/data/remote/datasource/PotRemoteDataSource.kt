package com.example.tbcworks.data.remote.datasource

import com.example.tbcworks.data.common.firestore.FirestoreHelper
import com.example.tbcworks.data.remote.model.pot.PotRequestDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PotRemoteDataSource @Inject constructor(
    private val firestoreHelper: FirestoreHelper
) {
    suspend fun addPot(userId: String, pot: PotRequestDto) =
        firestoreHelper.potsCollectionRef(userId).document(pot.id).set(pot).await()

    suspend fun getPots(userId: String) =
        firestoreHelper.potsCollectionRef(userId).get().await().toObjects(PotRequestDto::class.java)
}