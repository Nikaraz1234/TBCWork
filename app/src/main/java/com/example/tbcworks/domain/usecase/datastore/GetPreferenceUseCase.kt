package com.example.tbcworks.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.tbcworks.domain.repository.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(private val preferencesRepository: DataStoreManager)  {
    suspend fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}