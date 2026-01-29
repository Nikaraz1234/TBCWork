package com.example.mycomposeapp.data.service

import com.example.mycomposeapp.data.dto.StoryDto
import retrofit2.http.GET

interface StoryService {
    @GET("story")
    suspend fun getStories() : List<StoryDto>
}