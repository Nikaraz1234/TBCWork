package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.model.Location
import com.example.tbcworks.data.repository.LocationRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepositoryImpl
) {
    fun invoke(): Flow<List<Location>> = flow {
        val locations = repository.getLocations()
        emit(locations)
    }
}
