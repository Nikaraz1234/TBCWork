package com.example.composeapp.domain.keys

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val TOKEN = stringPreferencesKey(DataStoreKeys.TOKEN)
    val USERNAME = stringPreferencesKey(DataStoreKeys.USERNAME)
    val USER_TOKEN = stringPreferencesKey(DataStoreKeys.USER_TOKEN)
    val DARK_MODE = booleanPreferencesKey(DataStoreKeys.DARK_MODE)
}