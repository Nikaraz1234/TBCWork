package com.example.tbcworks.domain.usecase.pots

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Pot
import com.example.tbcworks.domain.repository.PotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddPotUseCase @Inject constructor(
    private val repository: PotRepository
) {
    suspend operator fun invoke(userId: String, pot: Pot) = repository.addPot(userId, pot)
}