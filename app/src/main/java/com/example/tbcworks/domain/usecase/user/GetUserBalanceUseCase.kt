package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.data.repository.UserRepositoryImpl
import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserBalanceUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {
    operator fun invoke(userId: String): Flow<Resource<Double>> =
        userRepository.getUserBalance(userId)

}
