package com.example.tbcworks.domain.usecase.pots

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.repository.PotRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeletePotUseCase @Inject constructor(
    private val repository: PotRepository
) {
    suspend operator fun invoke(potId: String): Flow<Resource<Unit>> {
        return repository.deletePot(potId)
    }
}