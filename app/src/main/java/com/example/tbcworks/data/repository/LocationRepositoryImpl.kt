package com.example.tbcworks.data.repository


import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.LocationService
import com.example.tbcworks.domain.model.Location
import com.example.tbcworks.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) : LocationRepository{

    override suspend fun getLocations(): List<Location> {
        return  locationService.getLocations().map { it.toDomain() }

    }
}