package com.example.tbcworks.domain.usecase.proto


import com.example.tbcworks.domain.model.GetUserData
import com.example.tbcworks.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadUserUseCase @Inject constructor(
    private val repository: UserDataRepository
) {
    operator fun invoke(): Flow<GetUserData> = repository.readUser()
}