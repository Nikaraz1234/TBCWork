package com.example.tbcworks.domain.usecase.datastore_pref

import com.example.tbcworks.data.common.dataStore.TokenDataStore
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore
) {
    suspend operator fun invoke(token: String, email: String) {
        tokenDataStore.saveValue(TokenDataStore.TOKEN_KEY, token)
        tokenDataStore.saveValue(TokenDataStore.EMAIL_KEY, email)
    }
}