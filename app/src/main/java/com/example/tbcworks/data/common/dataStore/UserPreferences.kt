package com.example.tbcworks.data.common.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.get

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class TokenDataStore(private val context: Context) {

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("auth_token")
        private val EMAIL_KEY = stringPreferencesKey("email_key")
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun removeToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }
    suspend fun saveEmail(username: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = username
        }
    }

    fun getEmail(): Flow<String?> =
        context.dataStore.data.map { it[EMAIL_KEY]
        }

    suspend fun removeEmail() {
        context.dataStore.edit { preferences ->
            preferences.remove(EMAIL_KEY)
        }
    }
}
