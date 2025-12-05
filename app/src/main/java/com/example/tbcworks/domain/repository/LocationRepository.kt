package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.model.Location

interface LocationRepository {
    suspend fun getLocations(): List<Location>
}