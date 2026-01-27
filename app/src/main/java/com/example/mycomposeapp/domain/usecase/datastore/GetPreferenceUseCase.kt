package com.example.mycomposeapp.domain.usecase.datastore


import androidx.datastore.preferences.core.Preferences
import com.example.mycomposeapp.domain.repository.DataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferenceUseCase @Inject constructor(private val preferencesRepository: DataStoreManager)  {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return preferencesRepository.getPreference(key, defaultValue)
    }
}