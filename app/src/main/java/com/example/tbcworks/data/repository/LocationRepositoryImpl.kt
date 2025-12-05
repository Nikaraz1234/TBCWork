package com.example.tbcworks.data.repository


import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.service.LocationService
import com.example.tbcworks.domain.model.Location
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationService: LocationService
) {

    suspend fun getLocations(): List<Location> {
        val result = locationService.getLocations().map { it.toDomain() }
        print("Api Result: $result")
        return result
    }
}