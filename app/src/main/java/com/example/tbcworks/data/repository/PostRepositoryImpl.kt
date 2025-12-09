package com.example.tbcworks.data.repository

import com.example.tbcworks.data.common.resource.HandleResponse
import com.example.tbcworks.data.local.dao.PostDao
import com.example.tbcworks.data.mapper.toDomain
import com.example.tbcworks.data.mapper.toEntity
import com.example.tbcworks.data.service.PostService
import com.example.tbcworks.domain.Resource
import com.example.tbcworks.domain.model.GetPost
import com.example.tbcworks.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val handleResponse: HandleResponse,
    private val postDao: PostDao
) : PostRepository{
    override fun getPosts(): Flow<Resource<List<GetPost>>> = flow {
        emit(Resource.Loading)

        handleResponse.safeApiCall {
            val posts = postService.getPosts()
            postDao.clearPosts()
            postDao.insertPosts(posts.map { it.toEntity() })
        }.collect { resource ->
            if (resource is Resource.Error) {
                emit(resource)
            }
        }
        emitAll(
            postDao.getAllPostsFlow().map { list ->
                Resource.Success(list.map { it.toDomain() })
            }
        )
    }
}