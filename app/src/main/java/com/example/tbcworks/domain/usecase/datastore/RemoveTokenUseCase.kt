package com.example.tbcworks.domain.usecase.datastore

import com.example.tbcworks.data.common.datastore.DataStoreKeys
import com.example.tbcworks.domain.repository.DataStoreRepository


class RemoveTokenUseCase(
    private val repo: DataStoreRepository
) {
    suspend operator fun invoke() {
        repo.remove(DataStoreKeys.TOKEN)
    }
}