package com.example.mycomposeapp.domain.usecase.post

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Post
import com.example.mycomposeapp.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> {
        return repository.getPosts()
    }
}
