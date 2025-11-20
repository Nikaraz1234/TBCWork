package com.example.tbcworks.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbcworks.data.common.dataStore.TokenDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : ViewModel() {

    private val _sideEffect = MutableSharedFlow<HomeSideEffect>()
    val sideEffect = _sideEffect.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.LogoutClicked -> logout()
            HomeEvent.UserListClicked -> navigateToDashboard()
        }
    }

    private fun logout() {
        viewModelScope.launch {
            tokenDataStore.removeToken()
            tokenDataStore.removeEmail()
            _sideEffect.emit(HomeSideEffect.NavigateToLogin)
        }
    }

    private fun navigateToDashboard() {
        viewModelScope.launch {
            _sideEffect.emit(HomeSideEffect.NavigateToDashboard)
        }
    }
}