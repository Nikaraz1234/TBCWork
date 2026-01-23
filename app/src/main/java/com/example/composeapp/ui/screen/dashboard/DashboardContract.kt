package com.example.composeapp.ui.screen.dashboard

import com.example.composeapp.ui.screen.dashboard.model.ChatModel

object DashboardContract {
    data class State(
        var isLoading: Boolean = false,
        var chats: List<ChatModel> = emptyList(),
        var filteredChats: List<ChatModel> = emptyList(),
        var error: String? = null,
        var searchQuery: String = ""
    )

    sealed class SideEffect {
        data class ShowError(val error: String): SideEffect()
    }

    sealed class Event {
        object LoadChats : Event()
        data class OnSearchQueryChanged(val query: String) : Event()
        object RetryLoadChats : Event()
    }

}
