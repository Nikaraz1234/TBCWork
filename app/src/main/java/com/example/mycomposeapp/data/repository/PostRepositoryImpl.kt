package com.example.mycomposeapp.data.repository

import com.example.mycomposeapp.data.common.HandleResponse
import com.example.mycomposeapp.data.extension.asResource
import com.example.mycomposeapp.data.mapper.toDomain
import com.example.mycomposeapp.data.service.PostService
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Post
import com.example.mycomposeapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl @Inject constructor(
    private val service: PostService,
    private val handleResponse: HandleResponse
) : PostRepository {

    override fun getPosts(): Flow<Resource<List<Post>>> {
        return handleResponse
            .safeApiCall { service.getPosts() }
            .map { resource ->
                resource.asResource { list ->
                    list.map { it.toDomain() }
                }
            }
    }
}