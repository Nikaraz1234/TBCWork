package com.example.tbcworks.domain.repository

interface StoryRepository {
    suspend fun getStories()
}