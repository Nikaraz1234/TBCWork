package com.example.composeapp.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.composeapp.domain.repository.DataStoreManager
import javax.inject.Inject

class RemovePreferencesUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
)  {
    suspend operator fun invoke(keys: List<Preferences.Key<*>>) {
        preferencesRepository.removePreferences(keys)
    }
}