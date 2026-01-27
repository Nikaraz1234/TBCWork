package com.example.mycomposeapp.domain.repository

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocations(): Flow<Resource<List<Location>>>
}
