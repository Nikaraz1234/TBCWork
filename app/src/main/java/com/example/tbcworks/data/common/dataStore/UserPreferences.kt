package com.example.tbcworks.data.common.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map



private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenDataStore(private val context: Context) {

    companion object {
        val TOKEN_KEY = stringPreferencesKey("auth_token")
        val EMAIL_KEY = stringPreferencesKey("email_key")
    }

    suspend fun <T> saveValue(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    fun <T> getValue(key: Preferences.Key<T>): Flow<T?> {
        return context.dataStore.data.map { prefs ->
            prefs[key]
        }
    }

    suspend fun <T> removeValue(key: Preferences.Key<T>) {
        context.dataStore.edit { prefs ->
            prefs.remove(key)
        }
    }
}
