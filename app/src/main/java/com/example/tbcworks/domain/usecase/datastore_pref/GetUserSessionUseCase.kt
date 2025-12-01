package com.example.tbcworks.domain.usecase.datastore_pref

import com.example.tbcworks.data.common.dataStore.TokenDataStore
import com.example.tbcworks.domain.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetUserSessionUseCase @Inject constructor(
    private val tokenDataStore: TokenDataStore
) {
    operator fun invoke(): Flow<UserSession> = combine(
        tokenDataStore.getValue(TokenDataStore.TOKEN_KEY),
        tokenDataStore.getValue(TokenDataStore.EMAIL_KEY)
    ) { token, email ->
        UserSession(token, email)
    }
}