package com.example.mycomposeapp.data.repository

import com.example.mycomposeapp.data.common.HandleResponse
import com.example.mycomposeapp.data.extension.asResource
import com.example.mycomposeapp.data.mapper.toDomain
import com.example.mycomposeapp.data.service.StoryService
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Story
import com.example.mycomposeapp.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepositoryImpl @Inject constructor(
    private val service: StoryService,
    private val handleResponse: HandleResponse
) : StoryRepository {

    override fun getStories(): Flow<Resource<List<Story>>> {
        return handleResponse
            .safeApiCall { service.getStories() }
            .map { resource ->
                resource.asResource { list ->
                    list.map { it.toDomain() }
                }
            }
    }
}