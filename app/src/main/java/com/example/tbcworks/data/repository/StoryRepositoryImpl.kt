package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.dao.StoryDao
import com.example.tbcworks.data.local.entity.StoryEntity
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.mapper.toEntity
import com.example.tbcworks.data.model.StoryDto
import com.example.tbcworks.data.service.StoryService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetStory
import com.example.tbcworks.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepositoryImpl @Inject constructor(
    private val storyService: StoryService,
    private val handleResponse: HandleResponse,
    private val storyDao: StoryDao
): StoryRepository {

    override fun getStories(): Flow<Resource<List<GetStory>>> = flow {
        emit(Resource.Loading)

        handleResponse.safeApiCall {
            val stories = storyService.getStories()
            storyDao.clearStories()
            storyDao.insertStories(stories.map { it.toEntity() })
        }.collect { resource ->
            if (resource is Resource.Error) emit(resource)
        }

        emitAll(
            storyDao.getAllStoriesFlow().map { entitiesList ->
                Resource.Success(entitiesList.map { it.toDomain() })
            }
        )


    }
}