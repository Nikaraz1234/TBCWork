package com.example.tbcworks.domain.usecase.user

import com.example.tbcworks.data.repository.UserRepositoryImpl
import com.example.tbcworks.domain.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WithdrawMoneyFromUserUseCase @Inject constructor(
    private val userRepository: UserRepositoryImpl
) {
    suspend operator fun invoke(userId: String, amount: Double): Flow<Resource<Unit>> {
        return userRepository.withdrawMoneyFromUser(userId, amount)
    }
}
