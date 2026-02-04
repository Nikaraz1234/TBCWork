package com.example.domain.usecase.datastore


import androidx.datastore.preferences.core.Preferences
import com.example.domain.repository.DataStoreManager
import kotlinx.coroutines.flow.Flow

class GetPreferenceUseCase(private val preferencesRepository: DataStoreManager)  {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}