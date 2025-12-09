package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetStory
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories() : Flow<Resource<List<GetStory>>>
}