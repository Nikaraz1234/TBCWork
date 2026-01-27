package com.example.mycomposeapp.domain.usecase.datastore

import androidx.datastore.preferences.core.Preferences
import com.example.mycomposeapp.domain.repository.DataStoreManager
import javax.inject.Inject

class RemovePreferenceUseCase @Inject constructor(
    private val preferencesRepository: DataStoreManager
)  {
    suspend operator fun invoke(keys: List<Preferences.Key<*>>) {
        preferencesRepository.removePreferences(keys)
    }
}