package com.example.tbcworks.data.auth.repository

import com.example.tbcworks.data.common.dataStore.UserData
import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val dataStore: DataStore<UserData>
) {
    suspend fun saveUser(first: String, last: String, email: String) {
        dataStore.updateData { currentData ->
            currentData.toBuilder()
                .setFirstName(first)
                .setLastName(last)
                .setEmail(email)
                .build()
        }
    }

    fun readUser(): Flow<UserData> = dataStore.data
}
