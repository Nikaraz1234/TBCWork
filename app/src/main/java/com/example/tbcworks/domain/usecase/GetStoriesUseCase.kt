package com.example.tbcworks.domain.usecase

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetStory
import com.example.tbcworks.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke(): Flow<Resource<List<GetStory>>> {
        return repository.getStories()
    }
}