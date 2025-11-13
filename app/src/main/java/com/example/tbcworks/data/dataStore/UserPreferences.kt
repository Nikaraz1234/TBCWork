package com.example.tbcworks.data.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

val TOKEN_KEY = stringPreferencesKey("auth_token")

suspend fun saveToken(context: Context, token: String) {
    context.dataStore.edit { preferences ->
        preferences[TOKEN_KEY] = token
    }
}

fun getToken(context: Context): Flow<String?> {
    return context.dataStore.data.map { preferences ->
        preferences[TOKEN_KEY]
    }
}

suspend fun removeToken(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.remove(TOKEN_KEY)
    }
}
