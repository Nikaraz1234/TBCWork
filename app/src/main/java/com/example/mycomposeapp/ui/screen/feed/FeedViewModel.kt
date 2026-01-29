package com.example.mycomposeapp.ui.screen.feed

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mycomposeapp.domain.Resource
import com.example.mycomposeapp.domain.usecase.story.GetStoriesUseCase
import com.example.mycomposeapp.domain.usecase.post.GetPostsUseCase
import com.example.mycomposeapp.ui.common.BaseViewModel
import com.example.mycomposeapp.ui.screen.feed.FeedContract.Event
import com.example.mycomposeapp.ui.screen.feed.FeedContract.SideEffect
import com.example.mycomposeapp.ui.screen.feed.FeedContract.State
import com.example.mycomposeapp.ui.screen.feed.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase
) : BaseViewModel<State, SideEffect, Event>(
    initialState = State()
) {

    fun onEvent(event: Event) {
        when (event) {
            Event.LoadStories -> loadStories()
            Event.LoadPosts -> loadPosts()
            Event.ClearError -> setState { copy(error = null) }
        }
    }


    private fun loadStories() {
        handleResponse(
            apiCall = { getStoriesUseCase() },
            onSuccess = { stories ->
                setState {
                    copy(
                        stories = stories.map { it.toPresentation() },
                        error = null,
                        isLoading = false
                    )
                }
            },
            onError = { message ->
                setState { copy(error = message, isLoading = false) }
                sendSideEffect(SideEffect.ShowSnackBar(message = message))
            },
            onLoading = {
                setState { copy(isLoading = true) }
            }
        )
    }

    private fun loadPosts() {
        handleResponse(
            apiCall = { getPostsUseCase() },
            onSuccess = { posts ->
                setState {
                    copy(
                        posts = posts.map { it.toPresentation() },
                        error = null,
                        isLoading = false
                    )
                }
            },
            onError = { message ->
                setState { copy(error = message, isLoading = false) }
                sendSideEffect(SideEffect.ShowSnackBar(message = message))
            },
            onLoading = {
                setState { copy(isLoading = true) }
            }
        )
    }
}
