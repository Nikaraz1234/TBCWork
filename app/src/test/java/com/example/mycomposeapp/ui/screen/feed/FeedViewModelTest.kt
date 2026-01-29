package com.example.mycomposeapp.ui.screen.feed

import com.example.mycomposeapp.MainDispatcherRule
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.model.Post
import com.example.mycomposeapp.domain.model.Story
import com.example.mycomposeapp.domain.usecase.post.GetPostsUseCase
import com.example.mycomposeapp.domain.usecase.story.GetStoriesUseCase
import com.example.mycomposeapp.ui.screen.feed.FeedContract.Event
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getStoriesUseCase: GetStoriesUseCase = mockk()
    private val getPostsUseCase: GetPostsUseCase = mockk()

    private lateinit var viewModel: FeedViewModel

    @Before
    fun setup() {
        viewModel = FeedViewModel(
            getStoriesUseCase = getStoriesUseCase,
            getPostsUseCase = getPostsUseCase
        )
    }

    @Test
    fun loadStories_success_updatesStateWithStories() = runTest {
        val stories = listOf(
            Story(id = 1, title = "Story", cover = "url")
        )

        coEvery { getStoriesUseCase() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(stories))
        }

        viewModel.onEvent(Event.LoadStories)
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertEquals(1, state.stories.size)
        assertFalse(state.isLoading)
        assertNull(state.error)

        coVerify(exactly = 1) { getStoriesUseCase() }
    }

    @Test
    fun loadStories_error_updatesErrorAndEmitsSnackBar() = runTest {
        val errorMessage = "Stories error"

        coEvery { getStoriesUseCase() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }

        viewModel.onEvent(Event.LoadStories)
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertEquals(errorMessage, state.error)
        assertFalse(state.isLoading)

        val sideEffect = viewModel.sideEffect.first()
        assertTrue(sideEffect is FeedContract.SideEffect.ShowSnackBar)
        assertEquals(
            errorMessage,
            (sideEffect as FeedContract.SideEffect.ShowSnackBar).message
        )

    }

    @Test
    fun loadPosts_success_updatesStateWithPosts() = runTest {
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

        coEvery { getPostsUseCase() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Success(posts))
        }

        viewModel.onEvent(Event.LoadPosts)
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertEquals(1, state.posts.size)
        assertFalse(state.isLoading)
        assertNull(state.error)

        coVerify(exactly = 1) { getPostsUseCase() }
    }

    @Test
    fun loadPosts_error_updatesErrorAndEmitsSnackBar() = runTest {
        val errorMessage = "Posts error"

        coEvery { getPostsUseCase() } returns flow {
            emit(Resource.Loading)
            emit(Resource.Error(errorMessage))
        }

        viewModel.onEvent(Event.LoadPosts)
        advanceUntilIdle()

        val state = viewModel.uiState.first()
        assertEquals(errorMessage, state.error)
        assertFalse(state.isLoading)

        val sideEffect = viewModel.sideEffect.first()
        assertTrue(sideEffect is FeedContract.SideEffect.ShowSnackBar)
        assertEquals(
            errorMessage,
            (sideEffect as FeedContract.SideEffect.ShowSnackBar).message
        )

    }

    @Test
    fun clearError_resetsErrorToNull() = runTest {
        coEvery { getStoriesUseCase() } returns flow {
            emit(Resource.Error("Error"))
        }

        viewModel.onEvent(Event.LoadStories)
        advanceUntilIdle()

        viewModel.onEvent(Event.ClearError)

        val state = viewModel.uiState.first()
        assertNull(state.error)
    }
}
