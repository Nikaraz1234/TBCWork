package com.example.mycomposeapp.domain.usecase.post

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Post
import com.example.mycomposeapp.domain.repository.PostRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostsUseCaseTest {

    private val repository: PostRepository = mockk()
    private lateinit var useCase: GetPostsUseCase

    @Before
    fun setup() {
        useCase = GetPostsUseCase(repository)
    }

    @Test
    fun invoke_returnsRepositoryFlow_andCallsRepositoryOnce() = runTest {
        // GIVEN
        val posts = listOf(
            Post(
                id = 1,
                avatar = null,
                postDate = 0L,
                firstName = "John",
                lastName = "Doe",
                images = emptyList(),
                commentsCount = 0,
                likesCount = 0,
                postDesc = null,
                canComment = true,
                canPostPhoto = true
            )
        )

        val repoFlow = flowOf(
            Resource.Loading,
            Resource.Success(posts)
        )

        every { repository.getPosts() } returns repoFlow

        // WHEN
        val emissions = useCase().toList()

        // THEN
        assertEquals(listOf(Resource.Loading, Resource.Success(posts)), emissions)
        coVerify(exactly = 1) { repository.getPosts() }
    }
}
