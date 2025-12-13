package com.example.tbcworks.domain.usecase.datastore

import com.example.tbcworks.data.common.datastore.DataStoreKeys
import com.example.tbcworks.domain.repository.DataStoreRepository

import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(
    private val repo: DataStoreRepository
) {
    suspend operator fun invoke(token: String) {
        repo.save(DataStoreKeys.TOKEN, token)
    }
}