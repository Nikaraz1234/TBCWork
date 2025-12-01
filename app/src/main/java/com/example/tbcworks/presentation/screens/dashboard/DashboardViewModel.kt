package com.example.tbcworks.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.tbcworks.domain.usecase.paged_users.GetPagedUsersUseCase
import com.example.tbcworks.presentation.screens.dashboard.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getPagedUsersUseCase: GetPagedUsersUseCase
) : ViewModel() {

    private val _usersPaging = MutableStateFlow<PagingData<UserModel>>(PagingData.empty())
    val usersPaging: StateFlow<PagingData<UserModel>> = _usersPaging

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.FetchUsers -> fetchUsers()
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            getPagedUsersUseCase()
                .map { pagingData ->
                    pagingData.map { it.toPresentation() }
                }
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _usersPaging.value = pagingData
                }
        }
    }
}
