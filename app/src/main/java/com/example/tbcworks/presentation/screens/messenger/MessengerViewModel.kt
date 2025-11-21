package com.example.tbcworks.presentation.screens.messenger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.models.User
import com.example.tbcworks.data.repository.MessengerRepository
import com.example.tbcworks.data.resource.Resource
import com.example.tbcworks.presentation.screens.messenger.MessengerSideEffect.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessengerViewModel @Inject constructor(
    private val repository: MessengerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MessengerState())
    val state: StateFlow<MessengerState> = _state

    private val _sideEffect = MutableSharedFlow<MessengerSideEffect>()
    val sideEffect: SharedFlow<MessengerSideEffect> = _sideEffect

    private var fullUserList: List<User> = emptyList()

    fun onEvent(event: MessengerEvent) {
        when(event) {
            is MessengerEvent.LoadUsers -> loadUsers()
            is MessengerEvent.FilterUsers -> filterUsers(event.searchText)
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = repository.getUsers()) {
                is Resource.Success -> {
                    fullUserList = result.data
                    _state.value = _state.value.copy(
                        users = result.data,
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(isLoading = false)
                    _sideEffect.emit(ShowError(result.message))
                }
                Resource.Loading -> {}
            }
        }
    }

    private fun filterUsers(query: String) {
        val filteredList = if (query.isBlank()) {
            fullUserList
        } else {
            fullUserList.filter { it.owner.contains(query, ignoreCase = true) }
        }
        _state.value = _state.value.copy(users = filteredList)
    }
}