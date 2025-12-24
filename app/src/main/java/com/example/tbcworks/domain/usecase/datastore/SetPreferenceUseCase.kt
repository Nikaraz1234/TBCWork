package com.example.tbcworks.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.tbcworks.domain.repository.DataStoreManager
import javax.inject.Inject

class SetPreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
) {
    suspend fun <T> invoke(key: Preferences.Key<T>, value: T) {
        preferencesRepository.setPreference(key, value)
    }
}