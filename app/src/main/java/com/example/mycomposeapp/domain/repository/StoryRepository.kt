package com.example.mycomposeapp.domain.repository

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    fun getStories(): Flow<Resource<List<Story>>>
}