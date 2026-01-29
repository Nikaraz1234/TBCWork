package com.example.mycomposeapp.domain.usecase.story

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Story
import com.example.mycomposeapp.domain.repository.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    operator fun invoke(): Flow<Resource<List<Story>>> {
        return repository.getStories()
    }
}
