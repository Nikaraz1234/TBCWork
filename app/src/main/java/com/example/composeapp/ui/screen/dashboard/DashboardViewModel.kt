package com.example.composeapp.ui.screen.dashboard

import com.example.composeapp.domain.usecase.GetChatsUseCase
import com.example.composeapp.ui.common.BaseViewModel
import com.example.composeapp.ui.screen.dashboard.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getChatsUseCase: GetChatsUseCase
) : BaseViewModel<DashboardContract.State, DashboardContract.SideEffect, DashboardContract.Event>(
    initialState = DashboardContract.State()
) {

    fun onEvent(event: DashboardContract.Event) {
        when(event) {
            DashboardContract.Event.LoadChats -> loadChats()
            is DashboardContract.Event.OnSearchQueryChanged -> searchChats(event.query)
            DashboardContract.Event.RetryLoadChats -> loadChats()
        }
    }

    private fun loadChats() {
        handleResponse(
            apiCall = { getChatsUseCase() },
            onSuccess = { chats ->
                val presentationList = chats.map { it.toPresentation() }
                println("Mapped chats: $presentationList")
                setState {
                    copy(
                        isLoading = false,
                        chats = presentationList,
                        filteredChats = presentationList,
                        error = null
                    )
                }
            },
            onError = { message ->
                setState { copy(isLoading = false) }
                sendSideEffect(DashboardContract.SideEffect.ShowError(message))
            },
            onLoading = {
                setState { copy(isLoading = true) }
            }
        )
    }

    private fun searchChats(query: String) {
        setState { copy(searchQuery = query) }
        val filtered = uiState.value.chats.filter { chat ->
            chat.owner.contains(query, ignoreCase = true) ||
                    chat.lastMessage.contains(query, ignoreCase = true)
        }
        setState { copy(filteredChats = filtered) }
    }

}
