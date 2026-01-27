package com.example.mycomposeapp.data.repository

import com.example.mycomposeapp.data.common.HandleResponse
import com.example.mycomposeapp.data.extension.asResource
import com.example.mycomposeapp.data.mapper.toDomain
import com.example.mycomposeapp.data.service.LocationService
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Location
import com.example.mycomposeapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val service: LocationService,
    private val handleResponse: HandleResponse
) : LocationRepository {

    override fun getLocations(): Flow<Resource<List<Location>>> {
        return handleResponse
            .safeApiCall { service.getLocations() }
            .map { resource ->
                resource.asResource { dtoList ->
                    dtoList.map { it.toDomain() }
                }
            }
    }
}
