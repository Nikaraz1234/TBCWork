package com.example.tbcworks.data.repository

import androidx.datastore.core.DataStore
import com.example.tbcworks.data.common.dataStore.UserData
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.domain.model.GetUserData
import com.example.tbcworks.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<UserData>
) : UserDataRepository{

    override suspend fun saveUser(first: String, last: String, email: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setFirstName(first)
                .setLastName(last)
                .setEmail(email)
                .build()
        }
    }

    override fun readUser(): Flow<GetUserData> =
        dataStore.data.map { it.toDomain() }
}

