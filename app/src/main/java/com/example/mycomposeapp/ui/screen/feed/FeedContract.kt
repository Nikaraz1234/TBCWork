package com.example.mycomposeapp.ui.screen.feed

import com.example.mycomposeapp.ui.screen.feed.model.PostModel
import com.example.mycomposeapp.ui.screen.feed.model.StoryModel

object FeedContract {

    data class State(
        val isLoading: Boolean = false,
        val error: String? = null,
        val stories: List<StoryModel> = emptyList(),
        val posts: List<PostModel> = emptyList()
    )

    sealed interface Event {
        data object LoadStories : Event
        data object LoadPosts : Event
        data object ClearError : Event
    }

    sealed interface SideEffect {
        data class ShowSnackBar(val message: String) : SideEffect
    }
}
