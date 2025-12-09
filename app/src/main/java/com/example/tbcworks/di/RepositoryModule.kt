package com.example.tbcworks.di

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.dao.PostDao
import com.example.tbcworks.data.local.dao.StoryDao
import com.example.tbcworks.data.repository.PostRepositoryImpl
import com.example.tbcworks.data.repository.StoryRepositoryImpl
import com.example.tbcworks.data.service.PostService
import com.example.tbcworks.data.service.StoryService
import com.example.tbcworks.domain.repository.PostRepository
import com.example.tbcworks.domain.repository.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(
        postService: PostService,
        handleResponse: HandleResponse,
        postDao: PostDao
    ): PostRepository {
        return PostRepositoryImpl(postService, handleResponse, postDao)
    }

    @Provides
    @Singleton
    fun provideStoryRepository(
        storyService: StoryService,
        handleResponse: HandleResponse,
        storyDao: StoryDao
    ): StoryRepository {
        return StoryRepositoryImpl(storyService, handleResponse, storyDao)
    }
}
