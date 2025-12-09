package com.example.tbcworks.domain.repository

import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetPost
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts() : Flow<Resource<List<GetPost>>>
}