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
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val handleResponse: HandleResponse,
    private val postDao: PostDao
) : PostRepository{
    override fun getPosts(): Flow<Resource<List<GetPost>>> {
        val api = handleResponse.safeApiCall {
            val posts = postService.getPosts()
            val entities = posts.map { it.toEntity() }
            postDao.clearPosts()
            postDao.insertPosts(entities)
        }
        return postDao.getAllPosts().map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }
}