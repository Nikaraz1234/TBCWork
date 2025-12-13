package com.example.tbcworks.data.common.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.tbcworks.domain.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    override suspend fun <T> save(key: String, value: T) {
        context.dataStore.edit { prefs ->
            when (value) {
                is String -> prefs[stringPreferencesKey(key)] = value
                is Int -> prefs[intPreferencesKey(key)] = value
                is Boolean -> prefs[booleanPreferencesKey(key)] = value
                is Float -> prefs[floatPreferencesKey(key)] = value
                is Long -> prefs[longPreferencesKey(key)] = value
                else -> error("Unsupported type")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(key: String, default: T?): Flow<T?> {
        return context.dataStore.data.map { prefs ->
            val prefKey = when (default) {
                is String? -> stringPreferencesKey(key)
                is Int? -> intPreferencesKey(key)
                is Boolean? -> booleanPreferencesKey(key)
                is Float? -> floatPreferencesKey(key)
                is Long? -> longPreferencesKey(key)
                else -> error("Unsupported type")
            } as Preferences.Key<T>

            prefs[prefKey] ?: default
        }
    }

    override suspend fun remove(key: String) {
        context.dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey(key))
        }
    }

    override fun getString(key: String, default: String?): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[stringPreferencesKey(key)] ?: default
        }
    }
}