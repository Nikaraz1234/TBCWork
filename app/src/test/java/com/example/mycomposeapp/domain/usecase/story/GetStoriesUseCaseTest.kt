package com.example.mycomposeapp.domain.usecase.story

import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Story
import com.example.mycomposeapp.domain.repository.StoryRepository
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
class GetStoriesUseCaseTest {

    private val repository: StoryRepository = mockk()
    private lateinit var useCase: GetStoriesUseCase

    @Before
    fun setup() {
        useCase = GetStoriesUseCase(repository)
    }

    @Test
    fun invoke_returnsRepositoryFlow_andCallsRepositoryOnce() = runTest {
        // GIVEN
        val stories = listOf(
            Story(
                id = 1,
                title = "Story",
                cover = "url"
            )
        )

        val repoFlow = flowOf(
            Resource.Loading,
            Resource.Success(stories)
        )

        every { repository.getStories() } returns repoFlow

        // WHEN
        val emissions = useCase().toList()

        // THEN
        assertEquals(listOf(Resource.Loading, Resource.Success(stories)), emissions)
        coVerify(exactly = 1) { repository.getStories() }
    }
}
