package com.example.tbcworks.presentation.screens.user_list

import androidx.lifecycle.viewModelScope
import com.example.tbcworks.domain.usecase.FetchUsersFromApiUseCase
import com.example.tbcworks.domain.usecase.GetUsersFromDbUseCase
import com.example.tbcworks.domain.usecase.NetworkCheckUseCase
import com.example.tbcworks.presentation.common.BaseViewModel
import com.example.tbcworks.presentation.screens.user_list.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersFromDbUseCase: GetUsersFromDbUseCase,
    private val fetchUsersFromApiUseCase: FetchUsersFromApiUseCase,
    private val networkCheckUseCase: NetworkCheckUseCase
) : BaseViewModel<UserListState, UserListSideEffect, UserListEvent>(UserListState()) {

    init {
        observeDbUsers()
        fetchUsersIfOnline()
    }

    fun onEvent(event: UserListEvent) {
        when (event) {
            is UserListEvent.LoadUsers -> observeDbUsers()
            is UserListEvent.RefreshUsers -> fetchUsersIfOnline()
        }
    }

    private fun observeDbUsers() {
        viewModelScope.launch {
            getUsersFromDbUseCase().collect { users ->
                setState { copy(users = users.map { it.toPresentation() }) }
            }
        }
    }

    private fun fetchUsersIfOnline() {
        val hasInternet = networkCheckUseCase()
        if (!hasInternet) {
            return
        }else{
            viewModelScope.launch {
                fetchUsersFromApiUseCase(hasInternet)
            }
        }


    }
}
