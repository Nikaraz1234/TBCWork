package com.example.tbcworks.domain.usecase.datastore_pref

import com.example.tbcworks.data.common.dataStore.TokenDataStore
import javax.inject.Inject

class ClearUserSessionUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore
) {
    suspend operator fun invoke() {
        tokenDataStore.removeValue(TokenDataStore.TOKEN_KEY)
        tokenDataStore.removeValue(TokenDataStore.EMAIL_KEY)
    }
}