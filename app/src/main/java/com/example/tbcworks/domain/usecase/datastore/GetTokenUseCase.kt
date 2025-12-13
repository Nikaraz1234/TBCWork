package com.example.tbcworks.domain.usecase.datastore

import com.example.tbcworks.data.common.datastore.DataStoreKeys
import com.example.tbcworks.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val repo: DataStoreRepository
) {
    operator fun invoke() = repo.get(DataStoreKeys.TOKEN, "")
}

