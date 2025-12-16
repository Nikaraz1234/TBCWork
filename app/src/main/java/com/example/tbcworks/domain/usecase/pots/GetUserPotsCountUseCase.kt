package com.example.tbcworks.domain.usecase.pots

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.PotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPotsCountUseCase @Inject constructor(
    private val potRepository: PotRepository
) {
    suspend operator fun invoke(userId: String): Flow<Resource<Int>> {
        return potRepository.getUserPotsCount(userId)
    }
}