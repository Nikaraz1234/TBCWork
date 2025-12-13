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
    private val collection = firestore.collection("pots")

    override suspend fun addPot(pot: Pot): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            val docRef = collection.document()
            val request = pot.copy(id = docRef.id).toRequest()
            docRef.set(request).await()
        }.map { it.asResource { } }
    }

    override suspend fun getPots(): Flow<Resource<List<Pot>>> {
        return handleResponse.safeApiCall {
            val pots = collection.get().await()
            pots.toObjects(PotResponseDto::class.java)
        }.map { it.asResource { list -> list.map { dto -> dto.toDomain() } } }
    }

    override suspend fun deletePot(potId: String): Flow<Resource<Unit>> {
        return handleResponse.safeApiCall {
            collection.document(potId).delete().await()
        }.map { it.asResource { Unit } }
    }
}
