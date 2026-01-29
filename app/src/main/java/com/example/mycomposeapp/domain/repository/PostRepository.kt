package com.example.mycomposeapp.domain.repository

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<Post>>>
}