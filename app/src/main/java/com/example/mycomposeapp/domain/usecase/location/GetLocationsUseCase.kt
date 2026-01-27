package com.example.mycomposeapp.domain.usecase.location

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Location
import com.example.mycomposeapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<Resource<List<Location>>> {
        return repository.getLocations()
    }
}