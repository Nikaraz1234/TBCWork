package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.extension.asResource
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.model.LocationResponseDto
import com.example.tbcworks.data.service.LocationService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.Location
import com.example.tbcworks.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val api: LocationService,
    private val handleResponse: HandleResponse
) : LocationRepository {

    override fun getLocations(): Flow<Resource<List<Location>>> {
        return handleResponse.safeApiCall {
            api.getLocations()
        }.map { resource ->
            resource.asResource { list ->
                list.map { it.toDomain() }
            }
        }
    }
}