package com.example.tbcworks.domain.usecase.pots

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.PotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddMoneyToPotUseCase @Inject constructor(
    private val repository: PotRepository
) {
    suspend operator fun invoke(userId: String, potId: String, amount: Double): Flow<Resource<Unit>> {
        return repository.addMoney(userId, potId, amount)
    }
}
