package com.example.tbcworks.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> save(key: String, value: T)
    fun <T> get(key: String, default: T? = null): Flow<T?>
    suspend fun remove(key: String)
    fun getString(key: String, default: String? = null): Flow<String?>
}