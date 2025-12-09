package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.dao.StoryDao
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.mapper.toEntity
import com.example.tbcworks.data.model.StoryDto
import com.example.tbcworks.data.service.StoryService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetStory
import com.example.tbcworks.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StoryRepositoryImpl @Inject constructor(
    private val storyService: StoryService,
    private val handleResponse: HandleResponse,
    private val storyDao: StoryDao
): StoryRepository {

    override fun getStories(): Flow<Resource<List<GetStory>>> {
        return handleResponse.safeApiCall {
            val stories: List<StoryDto> = storyService.getStories()
            val entities = stories.map { it.toEntity() }
            storyDao.insertStories(entities)
            storyDao.getAllStories().map { it.toDomain() }
        }
    }
}