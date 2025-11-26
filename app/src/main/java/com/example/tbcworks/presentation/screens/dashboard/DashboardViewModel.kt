package com.example.tbcworks.presentation.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.tbcworks.data.auth.models.User
import com.example.tbcworks.data.auth.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private lateinit var _usersPaging: Flow<PagingData<User>>
    val usersPaging: Flow<PagingData<User>> get() = _usersPaging

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.FetchUsers -> fetchUsers()
        }
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _usersPaging = userRepository.getPagedUsers()
                .cachedIn(viewModelScope) // Important for performance
        }
    }
}
